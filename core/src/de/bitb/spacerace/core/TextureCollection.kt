package de.bitb.spacerace.core

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Skin


object TextureCollection {
    private const val imageSize = 64

    val skin = Skin(Gdx.files.internal("uiskin.json"))

    val guiBackground: Texture
    val gameOverBackground: Texture
    val gameBackground: Texture

    val blackhole: Texture
    val fallingStar: Texture

    val greenPlanet: Texture
    val redPlanet: Texture
    val debrisField: Texture
    val minePlanet: Texture
    val goalPlanet: Texture
    val unknownPlanet: Texture

    val speederShipLanding1: Texture
    val speederShipLanding2: Texture
    val speederShipMoving1: Texture
    val speederShipMoving2: Texture
    val speederShipMoving3: Texture

    val raiderShipLanding1: Texture
    val raiderShipLanding2: Texture
    val raiderShipMoving1: Texture
    val raiderShipMoving2: Texture
    val raiderShipMoving3: Texture

    val fieldItemShop: Texture

    val slowMine: Texture


    init {
        guiBackground = Texture("background/bg_silver.png")
        gameOverBackground = Texture("background/bg_gameover.jpg")
        gameBackground = Texture("background/bg_star.png")
        gameBackground.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat)

        blackhole = Texture("objects/blackhole.png")
        fallingStar = Texture("objects/falling_star.png")
        slowMine = Texture("objects/item_mine.png")
        fieldItemShop = Texture("objects/field_item_shop.png")
        debrisField = Texture("objects/debrisship.png")

        //SET DEFAULT TEXTURE
        val defaultTexture = Pixmap(Gdx.files.internal("objects.png"))
        var section = Pixmap(imageSize, imageSize, Pixmap.Format.RGBA8888)
        section.drawPixmap(defaultTexture, imageSize * 0, imageSize * 0, imageSize * 2, imageSize * 3, imageSize, imageSize)
        speederShipLanding1 = Texture(section)
        section = Pixmap(imageSize, imageSize, Pixmap.Format.RGBA8888)
        section.drawPixmap(defaultTexture, imageSize * 0, imageSize * 0, imageSize * 3, imageSize * 3, imageSize, imageSize)
        speederShipLanding2 = Texture(section)
        //SPEEDER
        section = Pixmap(imageSize, imageSize, Pixmap.Format.RGBA8888)
        section.drawPixmap(defaultTexture, imageSize * 0, imageSize * 0, imageSize * 3, imageSize * 2, imageSize, imageSize)
        speederShipMoving1 = Texture(section)
        section = Pixmap(imageSize, imageSize, Pixmap.Format.RGBA8888)
        section.drawPixmap(defaultTexture, imageSize * 0, imageSize * 0, imageSize * 3, imageSize * 1, imageSize, imageSize)
        speederShipMoving2 = Texture(section)
        section = Pixmap(imageSize, imageSize, Pixmap.Format.RGBA8888)
        section.drawPixmap(defaultTexture, imageSize * 0, imageSize * 0, imageSize * 3, imageSize * 0, imageSize, imageSize)
        speederShipMoving3 = Texture(section)
        //RAIDER
        section = Pixmap(imageSize, imageSize, Pixmap.Format.RGBA8888)
        section.drawPixmap(defaultTexture, imageSize * 0, imageSize * 0, imageSize * 1, imageSize * 3, imageSize, imageSize)
        raiderShipLanding1 = Texture(section)
        section = Pixmap(imageSize, imageSize, Pixmap.Format.RGBA8888)
        section.drawPixmap(defaultTexture, imageSize * 0, imageSize * 0, imageSize * 0, imageSize * 3, imageSize, imageSize)
        raiderShipLanding2 = Texture(section)
        section = Pixmap(imageSize, imageSize, Pixmap.Format.RGBA8888)
        section.drawPixmap(defaultTexture, imageSize * 0, imageSize * 0, imageSize * 0, imageSize * 1, imageSize, imageSize)
        raiderShipMoving1 = Texture(section)
        section = Pixmap(imageSize, imageSize, Pixmap.Format.RGBA8888)
        section.drawPixmap(defaultTexture, imageSize * 0, imageSize * 0, imageSize * 1, imageSize * 1, imageSize, imageSize)
        raiderShipMoving2 = Texture(section)
        section = Pixmap(imageSize, imageSize, Pixmap.Format.RGBA8888)
        section.drawPixmap(defaultTexture, imageSize * 0, imageSize * 0, imageSize * 2, imageSize * 1, imageSize, imageSize)
        raiderShipMoving3 = Texture(section)

        //SET PLANET TEXTURE
        val planetTexture = Pixmap(Gdx.files.internal("objects/planets.png"))
        section = Pixmap(imageSize, imageSize, Pixmap.Format.RGBA8888)
        section.drawPixmap(planetTexture, imageSize * 0, imageSize * 0, (imageSize - 1) * 3, imageSize * 2, imageSize, imageSize)
        greenPlanet = Texture(section)

        section = Pixmap(imageSize, imageSize, Pixmap.Format.RGBA8888)
        section.drawPixmap(planetTexture, imageSize * 0, imageSize * 0, (imageSize - 1) * 3, imageSize * 0, imageSize, imageSize)
        redPlanet = Texture(section)

        section = Pixmap(imageSize, imageSize, Pixmap.Format.RGBA8888)
        section.drawPixmap(planetTexture, imageSize * 0, imageSize * 0, (imageSize * 2.4).toInt(), imageSize * 3, imageSize, imageSize)
        minePlanet = Texture(section)

        section = Pixmap(imageSize, imageSize, Pixmap.Format.RGBA8888)
        section.drawPixmap(planetTexture, imageSize * 0, imageSize * 0, imageSize * 2 - 2, imageSize * 0, imageSize, imageSize)
        goalPlanet = Texture(section)

        section = Pixmap(imageSize, imageSize, Pixmap.Format.RGBA8888)
        section.drawPixmap(planetTexture, imageSize * 0, imageSize * 0, imageSize * 6, imageSize * 3, imageSize, imageSize)
        unknownPlanet = Texture(section)

    }

//    private fun createDefaultTexture(){
//
//        val defaultTexture = Pixmap(Gdx.files.internal("objects.png"))
//        var section = Pixmap(imageSize, imageSize, Pixmap.Format.RGBA8888)
//        section.drawPixmap(defaultTexture, imageSize * 0, imageSize * 0, imageSize * 2, imageSize * 0, imageSize, imageSize)
//        blueField = Texture(section)
//        section.drawPixmap(defaultTexture, imageSize * 0, imageSize * 0, imageSize, imageSize, imageSize, imageSize)
//        redField = Texture(section)
//        section.drawPixmap(defaultTexture, imageSize * 0, imageSize * 0, imageSize * 0, imageSize, imageSize, imageSize)
//        greenField = Texture(section)
//        section.drawPixmap(defaultTexture, imageSize * 0, imageSize * 0, imageSize * 2, imageSize, imageSize, imageSize)
//        yellowField = Texture(section)
//        section.drawPixmap(defaultTexture, imageSize * 0, imageSize * 0, imageSize, imageSize * 0, imageSize, imageSize)
//        grayField = Texture(section)
//        section.drawPixmap(defaultTexture, imageSize * 0, imageSize * 0, imageSize * 0, imageSize * 0, imageSize, imageSize)
//        brownField = Texture(section)
//    }
}