# breakpoint-app

Created using `lein new chestnut app +edge +re-frame +less +http-kit`.

Quick run instructions:

* `lein repl`
* `(go)`
* `(cljs-repl)` (for the clojurescript repl; exit to clj repl with `:cljs/quit`)
* You should add the Giphy API key to `breakpoint-app.config/giphy-api-key` with the repl or in the source code.
* Auto-reload works for most things, but you need to explicitly `(reset)` in the clj repl after changing routes.clj.

## Exercises

### Ex. 1: Wire up the 'random' button

This should give you an idea how the unidirectional data flow in Re-frame works.

* You have a working API endpoint at /api/random (see routes.clj), assuming you have input an API key.
* In views.cljs, make the button do something:
  * You have a working effectful event handler with the keyword `:load-random-giphy` in events.cljs. This will fetch a random result for you from the above backend endpoint. `dispatch` it when the button is clicked! (see `re-frame.core/dispatch`)
  * The fetched result will be passed to the pure event handler with the keyword `:add-images`, also in events.cljs. Implement it to add the images to the app-db!
* To get the images from your app-db to the view components, you must first register a subscription. Do this in subs.cljs: make the `:images` subscription return the list of images you just made appear in app-db. (see `re-frame.core/subscribe`)
* In views.cljs you need to implement the `results-box` component:
  * Subscribe to the subscription you just created above using the `subscribe` function. This should go in a let binding outside the render function that the results-box function returns.
  * You can map the results to view components using the `results-item` function (these should go into the results-box div).

### Ex. 2: Create a 'clear all' button

You just need to replace the `:images` entry in the app-db using the same principles as in ex. 1, but this time it's all pure front-end code. Start by adding your own button by copy-pasting the 'random' button container...

### Ex. 3: Text search

You will need to do the full end-to-end implementation for this feature:

* In giphy.clj, implement the Giphy API call in the `search` function in a similar manner to the `random` function. Note that the `query-url` function can take two parameters, the first being the service name ("search"), and the second a map of the parameters (the key is "q" and the value the search terms). `parse-data` takes care of formatting the response and extracting the interesting bits of data.
* In routes.clj, add an endpoint that calls the search function above, not unlike the `random` endpoint. This time you will have to pass parameters, though.
* In views.clj, implement the `on-change` function for the text input.
  * Optimally, this should call a handler that sets the query string in the app-db; and dispatch another handler to perform the API query after a quiet period of no further typing in the text field. We have registered a `:dispatch-debounced` effects handler to this end in events.cljs.
  * The results from the query should be updated (added or replaced to the images list) as before. You got this.

### Ex. 4: Implement the 'remove' button functionality

The remove button should clear a single image from the list. It's probably easiest to do this using the `:id` key for each image and use `remove` on the images in app-db.

### Ex. 5: Implement the 'add to favorites' button

Create a new view component on the page that holds all images that are added to favorites.

### Ex. 6: Persist the favorites list in the backend; show them on page load

This entails creating endpoints for storing and loading the favorites list. You can use `defonce` to create a simple `atom` as your backend database.

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
