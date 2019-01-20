package de.bitb.spacerace.core

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.DEFAULT_BORDER
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.SMALL_BORDER
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.TINY_BORDER


object TextureCollection {

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

    var bumperShipLanding1: Texture
    var bumperShipLanding2: Texture
    var bumperShipMoving1: Texture
    var bumperShipMoving2: Texture
    var bumperShipMoving3: Texture

    val fieldItemShop: Texture

    val slowMine: Texture


    var injectedDirt: Texture
    var purpleCloud: Texture
    var purpleShuriken: Texture
    var bioCloud: Texture
    var flowerCloud: Texture
    var alienClaw: Texture
    var tinyFlowerCloud: Texture
    var tinyPurpleShuriken: Texture
    var tinyFlameShuriken: Texture
    var tinyShuriken: Texture
    var tinyPoint: Texture
    var tinyPinkShuriken: Texture
    var tinySpore: Texture
    var tinyLight: Texture

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

        //SET SHIP TEXTURE
        val shipTextures = Pixmap(Gdx.files.internal("textureregion/ships.png"))
        //SPEEDER
        speederShipLanding1 = createTexture(shipTextures, DEFAULT_BORDER * 2, DEFAULT_BORDER * 3)
        speederShipLanding2 = createTexture(shipTextures, DEFAULT_BORDER * 3, DEFAULT_BORDER * 3)
        speederShipMoving1 = createTexture(shipTextures, DEFAULT_BORDER * 3, DEFAULT_BORDER * 2)
        speederShipMoving2 = createTexture(shipTextures, DEFAULT_BORDER * 3, DEFAULT_BORDER * 1)
        speederShipMoving3 = createTexture(shipTextures, DEFAULT_BORDER * 3, DEFAULT_BORDER * 0)
        //RAIDER
        raiderShipLanding1 = createTexture(shipTextures, DEFAULT_BORDER * 1, DEFAULT_BORDER * 3)
        raiderShipLanding2 = createTexture(shipTextures, DEFAULT_BORDER * 0, DEFAULT_BORDER * 3)
        raiderShipMoving1 = createTexture(shipTextures, DEFAULT_BORDER * 1, DEFAULT_BORDER * 1)
        raiderShipMoving2 = createTexture(shipTextures, DEFAULT_BORDER * 2, DEFAULT_BORDER * 2)
        raiderShipMoving3 = createTexture(shipTextures, DEFAULT_BORDER * 0, DEFAULT_BORDER * 1)
        //BUMPER
        bumperShipLanding1 = createTexture(shipTextures, DEFAULT_BORDER * 1, DEFAULT_BORDER * 2)
        bumperShipLanding2 = createTexture(shipTextures, DEFAULT_BORDER * 2, DEFAULT_BORDER * 1)
        bumperShipMoving1 = createTexture(shipTextures, DEFAULT_BORDER * 2, DEFAULT_BORDER * 0)
        bumperShipMoving2 = createTexture(shipTextures, DEFAULT_BORDER * 1, DEFAULT_BORDER * 0)
        bumperShipMoving3 = createTexture(shipTextures, DEFAULT_BORDER * 0, DEFAULT_BORDER * 0)

        //SET PLANET TEXTURE
        val planetTexture = Pixmap(Gdx.files.internal("objects/planets.png"))
        greenPlanet = createTexture(planetTexture, (DEFAULT_BORDER - 1) * 3, DEFAULT_BORDER * 2)
        redPlanet = createTexture(planetTexture, (DEFAULT_BORDER - 1) * 3, DEFAULT_BORDER * 0)
        minePlanet = createTexture(planetTexture, (DEFAULT_BORDER * 2.4).toInt(), DEFAULT_BORDER * 3)
        goalPlanet = createTexture(planetTexture, DEFAULT_BORDER * 2 - 2, DEFAULT_BORDER * 0)
        unknownPlanet = createTexture(planetTexture, DEFAULT_BORDER * 6, DEFAULT_BORDER * 3)

        //SET OBJECT TEXTURE
        val objectTextures = Pixmap(Gdx.files.internal("textureregion/objects.png"))
        injectedDirt = createTexture(objectTextures, SMALL_BORDER * 0, SMALL_BORDER * 0)
        purpleCloud = createTexture(objectTextures, SMALL_BORDER * 0, SMALL_BORDER * 1)
        purpleShuriken = createTexture(objectTextures, SMALL_BORDER * 1, SMALL_BORDER * 1)
        bioCloud = createTexture(objectTextures, SMALL_BORDER * 1, SMALL_BORDER * 0)
        flowerCloud = createTexture(objectTextures, SMALL_BORDER * 2, SMALL_BORDER * 0)
        alienClaw = createTexture(objectTextures, SMALL_BORDER * 2, SMALL_BORDER * 1)

        tinyFlowerCloud = createTexture(objectTextures, TINY_BORDER * 6, SMALL_BORDER * 0)
        tinyPurpleShuriken = createTexture(objectTextures, TINY_BORDER * 7, SMALL_BORDER * 0)
        tinyFlameShuriken = createTexture(objectTextures, TINY_BORDER * 6, SMALL_BORDER * 1)
        tinyShuriken = createTexture(objectTextures, TINY_BORDER * 7, SMALL_BORDER * 1)
        tinyPoint = createTexture(objectTextures, TINY_BORDER * 6, SMALL_BORDER * 2)
        tinyPinkShuriken = createTexture(objectTextures, TINY_BORDER * 7, SMALL_BORDER * 2)
        tinySpore = createTexture(objectTextures, TINY_BORDER * 6, SMALL_BORDER * 3)
        tinyLight = createTexture(objectTextures, TINY_BORDER * 7, SMALL_BORDER * 3)
    }

    fun createTexture(defaultTexture: Pixmap, posX: Int, posY: Int): Texture {
        val section = Pixmap(DEFAULT_BORDER, DEFAULT_BORDER, Pixmap.Format.RGBA8888)
        section.drawPixmap(defaultTexture, 0, 0, posX, posY, DEFAULT_BORDER, DEFAULT_BORDER)
        return Texture(section)
    }
}