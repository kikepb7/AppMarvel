package com.enriquepalmadev.appmarvel.data.repository

import com.enriquepalmadev.appmarvel.domain.model.ComicModel

class ComicProvider {

    // As a Java static class. We can access to them without create an instance
    companion object {

        val comicsLists = listOf(
            ComicModel(
                1,
                "Spider-man: Shadow of the Green Goblin (2024) #1",
                "April 03, 2024",
                "J.M. DeMatteis",
                "Michelle Sta. Maria",
                "NORMAN OSBORN WAS NOT THE FIRST GOBLIN! Norman Osborn is the GREEN GOBLIN you know. But he is NOT the ORIGINAL GOBLIN! Learn the shocking secrets of the PROTO-GOBLIN, and its dramatic connection to the Osborn family! What role does a young Peter Parker, who has not yet understood his great power and responsibility, play in this unfolding of events? J.M. DEMATTEIS (SPIDER-MAN: KRAVEN'S LAST HUNT) continues to build his legacy and the mythos of classic SPIDER-LORE, this time paired with rising star MICHAEL STA. MARIA!",
                9.99,
                "https://cdn.marvel.com/u/prod/marvel/i/mg/f/e0/6601d3046216f/detail.jpg"
            ),
            ComicModel(
                2,
                "Deadpool (2024), #1",
                "April 03, 2024",
                "Cody Ziglar",
                "Roge Antonio",
                "A NEW ERA FOR THE MERC WITH A MOUTH, AND A GUN, AND A SWORD... CODY ZIGLAR (Futurama, Miles Morales: Spider-Man) has a wild ride planned for the Merc with the mouth! Introducing a terrifying new villain who won't stop until he catches Wade in his DEATH GRIP. But all work and no play makes Deadpool a very dead boy!",
                12.99,
                "https://cdn.marvel.com/u/prod/marvel/i/mg/8/03/6601d3468731f/detail.jpg"
            ),
            ComicModel(
                3,
                "Vengeance of the Moon Knight (2024) #4",
                "April 03, 2024",
                "Jed Mackay",
                "Alessandro Cappuccio",
                "BRAWL IN THE FAMILY! As the fearsome HUNTER'S MOON, Yehya Badr is the brother to the fallen MOON KNIGHT, Marc Spector. But there's an impostor loose in the city, wearing his brother's face - and Badr intends to find out who they are, BY ANY MEANS NECESSARY!",
                9.99,
                "https://cdn.marvel.com/u/prod/marvel/i/mg/6/40/6601d32692dce/detail.jpg"
            ),
            ComicModel(
                4,
                "Marvel Super Heroes Secret Wars Facsimile Edition (2024) #4",
                "April 03, 2024",
                "Jim Shooter",
                "Bob Layton",
                "Beneath one hundred and fifty billion tons stands the Hulk - and he's not happy! As Marvel's facsimile treatment of the finest super-hero event of all continues, the villains of Battleworld destroy the heroes' stronghold - and drop a mountain on them! Only a few heroes escape this fate: Thor is left to battle alone, while the Wasp is held captive by Magneto - and the X-Men propose a truce with their longtime foe! Meanwhile, Doctor Doom takes revenge on one of his evil allies! With most of the forces of good buried alive under an utterly unimaginable weight, can even the might of the Hulk keep them from death? It's perhaps the Green Goliath's finest hour in one of the all-time great Marvel comic books, boldly re-presented in its original form, ads and all! Reprinting MARVEL SUPER HEROES SECRET WARS #4",
                9.99,
                "https://cdn.marvel.com/u/prod/marvel/i/mg/2/a0/6601d33db5601/detail.jpg"
            ),
            ComicModel(
                5,
                "X-Men (2021) #33",
                "April 03, 2024",
                "Gerry Duggan",
                "Joshua Cassara",
                "X-MEN X-SSEMBLE! If there were ever a time to rally the troops and take the fight to the enemy, it's NOW! Stand side by side with the X-Men as they head for their final stand! They can't stop ALL of us!",
                9.99,
                "https://cdn.marvel.com/u/prod/marvel/i/mg/f/60/6601d3283c010/detail.jpg"
            ),
            ComicModel(
                6,
                "Alien: Black, White & Blood (2024) #3",
                "April 03, 2024",
                "Collin Kelly, Jackson Lanzing, Cody Ziglar, Steve Foxe",
                "JMichael Dowling, Tommaso Bianchi, Claire Roe",
                "THE STAR-STUDDED KILLFEST CONTINUES! In \"Utopia,\" Collin Kelly, Jackson Lanzing and Michael Dowling take you through the collapse of a civilization that believed it could overcome the worst of human nature to live in permanent peace. But in the war against the Xenomorphs overtaking their home, the citizens of the ship Forward find themselves breaking every principle… Then, in \"Gear in the Machine,\" Cody Ziglar goes guts-deep into the core of human evil, while in \"Lucky,\" Steve Foxe flips the viewpoint in a way that will tug at the heartstrings of even the most seasoned Alien fan. An unmissable piece of the universe-spanning franchise!",
                9.99,
                "https://cdn.marvel.com/u/prod/marvel/i/mg/8/d0/6601d35ab70cf/detail.jpg"
            ),

            )
    }
}