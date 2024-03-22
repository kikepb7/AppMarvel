package com.enriquepalmadev.appmarvel.model

class ComicProvider {

    // As a Java static class. We can access to them without create a class instance
    companion object {
        private var currentComic = 0

        // List of images when the customer click on the screen
        private val images = listOf(
            ComicsModel("https://www.milcomics.com/1306405-large_default/spiderman-01.jpg"),
            ComicsModel("https://images.cdn2.buscalibre.com/fit-in/360x360/2e/9e/2e9ef3597f6c36fb4369fded8f3ddfd5.jpg"),
            ComicsModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTyi4gf3V38lYQ8HwDwG7MuiJh0UKLRxGSJIXxzLP0fOKrjvGJ3vrkpWxTlf6xk71Nmd1U&usqp=CAU")
        )

        fun random(): ComicsModel {
            val position = (0..2).random()

            return images[position]
        }

        fun getNextComic(): ComicsModel {
            val nextComic = (currentComic + 1) % images.size
            currentComic = nextComic

            return images[nextComic]
        }
    }
}