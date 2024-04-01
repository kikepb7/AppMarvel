package com.enriquepalmadev.appmarvel.model

class ComicProvider {

    // As a Java static class. We can access to them without create an instance
    companion object {

        val comicsList = listOf(
            Comic(1, "Spider-man: 1", "Primer comic de Spider-man", 9.99, "https://www.milcomics.com/1306405-large_default/spiderman-01.jpg"),
            Comic(2, "Spider-man: 2", "Segundo comic de Spider-man", 12.99, "https://images.cdn2.buscalibre.com/fit-in/360x360/2e/9e/2e9ef3597f6c36fb4369fded8f3ddfd5.jpg"),
            Comic(3, "Spider-man: 3", "Tercer comic de Spider-man", 15.99, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTyi4gf3V38lYQ8HwDwG7MuiJh0UKLRxGSJIXxzLP0fOKrjvGJ3vrkpWxTlf6xk71Nmd1U&usqp=CAU") ,
            Comic(4, "Iron man: 1", "Primer comic de Iron man", 9.99, "https://www.zonanegativa.com/imagenes/2022/08/the_invincible_iron_man_1_2022.jpg"),
            Comic(5, "Iron man: 2", "Segundo comic de Iron man", 12.99, "https://i.pinimg.com/736x/9d/b3/a8/9db3a8156aae930919ca3869e1ed11ff.jpg"),
            Comic(6, "Iron man: 3", "Tercer comic de Iron man", 15.99, "https://www.comicverso.com/wp-content/uploads/2021/02/Iron-Man-Extremis-portada.jpg")
        )
    }
}