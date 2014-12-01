form-juice
==========

Data squeezed from juicy forms in ClojureScript. The juice is extracted from
real oranges only, with no jQuery additives!
[terjesb](https://github.com/terjesb) gently provided all the code through
[this gist](https://gist.github.com/terjesb/5272131).
The gist description says:

<img height="225px"
     align="right"
     src="http://upload.wikimedia.org/wikipedia/commons/9/9f/PikiWiki_Israel_10106_Gan-Shmuel_-_in_the_juice_factory_1987.jpg">
</img>
> How to get form data in a ClojureScript app, other than getting values
separately? I have a page with many instances of the same form, and a button to
submit each form. I couldn't find anything obvious in Domina, Enfocus or Dommy. 
Then I found goog.dom.forms.getFormDataMap, but the goog.structs.Map it returns
doesn't seem to be directly usable from ClojureScript.
I did find some work by chouser that uses extend-type on that Map to make it
like a regular map, but it still has some arrays in it.
goog.dom.forms.getFormDataString returns a x-www-url-encoded string of the form
data. I then ported the form-decode from ring.util.codec, and it seems to work
very well - returning the form data in a map.

### Getting the juice and related stuff
----------
Add the following to the `:dependencies` vector of your `project.clj` file:

[![clojars version](https://clojars.org/form-juice/latest-version.svg?raw=true)]
(https://clojars.org/form-juice)

### I'm thirsty!
----------
First add `[cljs-ajax "0.2.6"]` to the `:dependencies` vector of your
`project.clj` file.

This is how I drink:
```clojure
(ns app.views.something
  (:require [hiccup.def :refer [defelem]]))

(defelem some-form []
  [:form {:method "post" :action "/resource"}
    [:button {:type "submit" :onclick "app.something.post(event)"}
      "Submit"]])
```

```clojure
(ns app.something
  (:require form.juice
            [ajax.core :refer [POST]]))

(defn post [event]
  (.preventDefault event) ; prevents default event of form submission to fire
  (POST "/resource"
    {:params (form.juice/squeeze event)
     :handler (fn [_] (.log js/console "seiko shimashita! (*＾▽＾)／"))
     :error-handler (fn [_] (.log js/console "fukaku shimashita ｡ﾟ(ﾟ´Д｀ﾟ)ﾟ｡"))
     :format :raw}))
```

### Why not join this list of limonades made from form-juice?
----------
[kanasubs.com](http://www.kanasubs.com) — Convert raw subtitles in Kanji to
Kana or Romaji online.

### Random license
----------
Copyright (C) 2014 Carlos C. Fontes.

Licensed under the
[Apache License, Version 2.0](https://www.apache.org/licenses/LICENSE-2.0).
