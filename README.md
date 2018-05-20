# breakpoint-app

Created using `lein new chestnut app +edge +re-frame +less +http-kit`.

Quick run instructions:

* `lein repl`
* `(go)`
* `(cljs-repl)` (for the clojurescript repl; exit to clj repl with `:cljs/quit`)
* You should add the Giphy API key to `breakpoint-app.config/giphy-api-key` with the repl or in the source code.
* Auto-reload works for most things, but you need to explicitly `(reset)` in the clj repl after changing routes.clj.

## Examples / Exercises

### Demo 1: Toggle the animation on/off with a button click

### Exercise 1: Toggle the background color with a button click

This should give you an idea of how the unidirectional data flow in re-frame works.

* Start off by creating a new button. You can simply copy-paste the `animation-toggle` function to some other name, like `color-change` (you'll also want to change the text). To include the component in the page, you will also need to include it in the `main-panel` just like the existing `[animation-toggle]`, maybe right after it.
* Now, we need to use the button to dispatch an event that sets the application state's background color property to a desired value. To do this, we will use the `re-frame.core/dispatch` function in the button's on-click handler. The dispatch function will take a vector parameter which will contain a keyword as the event's name, and possibly some parameters after that. A example function that performs a dispatch: `#(dispatch [:color-event :black])`.
* Next, we should handle the event that we dispatch when the button is clicked. This is done in events.cljs, where we will create the event handler using `re-frame.core/reg-event-db`. This function is used to register a pure function that will update our application state. The parameters for the `reg-event-db` function are the event's name (a keyword) and the handler function. The function will receive as parameters the current app state, `db`, and a vector that holds the event name, and any possible parameters passed - in other words, the parameter given to the dispatch function. The function should return the updated application state. The event registration will look like: `(reg-event-db :color-event (fn [db [_ color]] (assoc db :background-color color))))`. This will set the `db` map's entry with the key `:background-color` to the value given.
* In order to refer to the app-db, and the color value therein, we must create a subscription. Subscriptions are used to access the app-db and their values will change whenever the app-db does. We will add a subscription in subs.cljs using the `re-frame.core/reg-sub` function. The usage looks much like `reg-event-db`: the parameters are the subscription's name (a keyword) and a function that receives the app-db map and should return data that is derived from the app-db. In our case, we just need to access the map value we just added: `(reg-sub :background-color (fn [db] (:background-color db)))`.
* Now we have access to the color, and just need to use it in our view. We have the necessary styles in place, so we can just set the `.black-background` style to the `.main-wrapper` div. To subscribe to the subscription created above, use the `re-frame.core/subscribe` function, providing the subscription name as a parameter. It will return the subscription, but you will need to dereference the subscription to get the contained value: `@(subscribe [:background-color])`. To put it all together, add a properties map to the `.main-wrapper` div (as its first element), and use the subscription: `{:class (when (= @(subscribe [:background-color]) :black) "black-background")}`.
* This will only allow you to set the background to black! How about making the button a toggle, or adding another button for the sky blue background?

### Exercise 2: Implement the 'Random Giphy' button.

This involves side-effectful event handlers — the backend API has been provided for you.

* Again, the "Load random" button needs to dispatch an event.
* We also need to create a handler for the event. This time we will need to do an AJAX call to the REST API, so it's a bit different from before. To help you along, we have provided a handler in events.cljs, which has been commented out. Un-commenting it should be enough for now (just note that you have matching event names in the dispatch and handler.)
* The handler has an `on-success` property, which is another dispatch, so we need to create a handler for that as well. Implement it using `reg-event-db`. The handler function will receive the JSON response as a parameter, so you can access it just like the `color` parameter in Ex. 1. The response contains a vector of images that should be appended to the `:images` entry in the app-db. You can do this in a few ways using `update`, the shortest probably being the function `into`, or a combination of `conj` and `flatten`.
* Next, create a subscription for the images. A stub is included and commented out in subs.cljs.
* To complete the final step of rendering the images, see the function `results-box` in views.cljs. In order to render an image, you can use the provided function `results-item` which takes one image as a parameter. Put the list of images inside `[:div.results-box]`, where you can use the `map` function to turn the list of image data (accessed with `@(subscribe [:the-subscription-you-just-created])`) into images.
* Once you get everything working, you will see some React warnings to the effect of `Every element in a seq should have a unique :key`. You can do this by adding the `:key` property to the outer div that the function `results-item` returns, with something unique as the value.
* If you have time to spare, how adding about YET ANOTHER button, which clears the list of images?

## Development

Open a terminal and type `lein repl` to start a Clojure REPL
(interactive prompt).

In the REPL, type

```clojure
(go)
(cljs-repl)
```

The call to `(go)` starts the Figwheel server at port 3449, which takes care of
live reloading ClojureScript code and CSS, and the app server at port 10555
which forwards requests to the http-handler you define.

Running `(cljs-repl)` starts the Figwheel ClojureScript REPL. Evaluating
expressions here will only work once you've loaded the page, so the browser can
connect to Figwheel.

When you see the line `Successfully compiled "resources/public/app.js" in 21.36
seconds.`, you're ready to go. Browse to `http://localhost:10555` and enjoy.

**Attention: It is not needed to run `lein figwheel` separately. Instead `(go)`
launches Figwheel directly from the REPL**

## Trying it out

If all is well you now have a browser window saying 'Hello Chestnut',
and a REPL prompt that looks like `cljs.user=>`.

Open `resources/public/css/style.css` and change some styling of the
H1 element. Notice how it's updated instantly in the browser.

Open `src/cljs/breakpoint-app/core.cljs`, and change `dom/h1` to
`dom/h2`. As soon as you save the file, your browser is updated.

In the REPL, type

```
(ns breakpoint-app.core)
(swap! app-state assoc :text "Interactivity FTW")
```

Notice again how the browser updates.

### Lighttable

Lighttable provides a tighter integration for live coding with an inline
browser-tab. Rather than evaluating cljs on the command line with the Figwheel
REPL, you can evaluate code and preview pages inside Lighttable.

Steps: After running `(go)`, open a browser tab in Lighttable. Open a cljs file
from within a project, go to the end of an s-expression and hit Cmd-ENT.
Lighttable will ask you which client to connect. Click 'Connect a client' and
select 'Browser'. Browse to [http://localhost:10555](http://localhost:10555)

View LT's console to see a Chrome js console.

Hereafter, you can save a file and see changes or evaluate cljs code (without
saving a file).

### Emacs/CIDER

CIDER is able to start both a Clojure and a ClojureScript REPL simultaneously,
so you can interact both with the browser, and with the server. The command to
do this is `M-x cider-jack-in-clojurescript`.

We need to tell CIDER how to start a browser-connected Figwheel REPL though,
otherwise it will use a JavaScript engine provided by the JVM, and you won't be
able to interact with your running app.

Put this in your Emacs configuration (`~/.emacs.d/init.el` or `~/.emacs`)

``` emacs-lisp
(setq cider-cljs-lein-repl
      "(do (user/go)
           (user/cljs-repl))")
```

Now `M-x cider-jack-in-clojurescript` (shortcut: `C-c M-J`, that's a capital
"J", so `Meta-Shift-j`), point your browser at `http://localhost:10555`, and
you're good to go.

## Testing

To run the Clojure tests, use

``` shell
lein test
```

To run the Clojurescript you use [doo](https://github.com/bensu/doo). This can
run your tests against a variety of JavaScript implementations, but in the
browser and "headless". For example, to test with PhantomJS, use

``` shell
lein doo phantom
```

## Deploying to Heroku

This assumes you have a
[Heroku account](https://signup.heroku.com/dc), have installed the
[Heroku toolbelt](https://toolbelt.heroku.com/), and have done a
`heroku login` before.

``` sh
git init
git add -A
git commit
heroku create
git push heroku master:master
heroku open
```

## Running with Foreman

Heroku uses [Foreman](http://ddollar.github.io/foreman/) to run your
app, which uses the `Procfile` in your repository to figure out which
server command to run. Heroku also compiles and runs your code with a
Leiningen "production" profile, instead of "dev". To locally simulate
what Heroku does you can do:

``` sh
lein with-profile -dev,+production uberjar && foreman start
```

Now your app is running at
[http://localhost:5000](http://localhost:5000) in production mode.

## License

Copyright © 2016 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

## Chestnut

Created with [Chestnut](http://plexus.github.io/chestnut/) 0.16.0 (67651e9d).
