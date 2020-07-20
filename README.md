# currency-exchange-rates

Currency exchange rate converter that uses a public Revolut API for the exchange rates

# Tech stack

This project is built purely with Kotlin with an MVVM design pattern, Retrofit to fetch the API data, Koin to manage dependencies and Rx to manage asynchronous tasks.

# Why did you choose to do that?
I'm pretty sure you already know why I went with Kotlin. I mean, come on.

MVVM is just more fun to play around with than MVP, even though I can't really say it's inherently better (I simply love LiveData).

Retrofit is my go-to tool when it comes to getting data from the internet. Easy to use and pretty straightforward!

Koin... the eternal dilemma with Koin and Dagger. Honestly I just went with Koin because I feel like it is designed for this kind of jobs. Small apps without a lot of dependencies. Dagger is superior in every way, but it would be overkill for this project.

When it comes down to Rx or Coroutines, I always choose Rx simply because that's what I have experience with. Coroutines are very good, but I feel like I have more control with Rx.

