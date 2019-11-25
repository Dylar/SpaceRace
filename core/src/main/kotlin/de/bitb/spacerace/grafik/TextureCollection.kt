package de.bitb.spacerace.grafik

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.DEFAULT_BORDER
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.SMALL_BORDER
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.TINY_BORDER
import de.bitb.spacerace.grafik.model.effects.SimpleAnimation
import de.bitb.spacerace.grafik.model.objecthandling.BaseAnimation
import de.bitb.spacerace.grafik.model.player.PlayerAnimation


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


    var infectedDirt: Texture
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

    var explosionFrame1: Texture
    var explosionFrame2: Texture
    var explosionFrame3: Texture
    var explosionFrame4: Texture
    var explosionFrame5: Texture
    var explosionFrame6: Texture
    var explosionFrame7: Texture
    var explosionFrame8: Texture
    var explosionFrame9: Texture
    var explosionFrame10: Texture
    var explosionFrame11: Texture
    var explosionFrame12: Texture
    var explosionFrame13: Texture
    var explosionFrame14: Texture
    var explosionFrame15: Texture
    var explosionFrame16: Texture

    val explosionAnimation: BaseAnimation

    init {
        guiBackground = Texture(Gdx.files.internal("background/bg_silver.png"), true)
//        buttonBackground = Texture(Gdx.files.internal("gui/reg_0000_save_button.9.png"), true)
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
        infectedDirt = createTexture(objectTextures, SMALL_BORDER * 0, SMALL_BORDER * 0, SMALL_BORDER)
        purpleCloud = createTexture(objectTextures, SMALL_BORDER * 0, SMALL_BORDER * 1, SMALL_BORDER)
        purpleShuriken = createTexture(objectTextures, SMALL_BORDER * 1, SMALL_BORDER * 1, SMALL_BORDER)
        bioCloud = createTexture(objectTextures, SMALL_BORDER * 1, SMALL_BORDER * 0, SMALL_BORDER)
        flowerCloud = createTexture(objectTextures, SMALL_BORDER * 2, SMALL_BORDER * 0, SMALL_BORDER)
        alienClaw = createTexture(objectTextures, SMALL_BORDER * 2, SMALL_BORDER * 1, SMALL_BORDER)

        tinyFlowerCloud = createTexture(objectTextures, TINY_BORDER * 6, TINY_BORDER * 0, TINY_BORDER)
        tinyPurpleShuriken = createTexture(objectTextures, TINY_BORDER * 7, TINY_BORDER * 0, TINY_BORDER)
        tinyFlameShuriken = createTexture(objectTextures, TINY_BORDER * 6, TINY_BORDER * 1, TINY_BORDER)
        tinyShuriken = createTexture(objectTextures, TINY_BORDER * 7, TINY_BORDER * 1, TINY_BORDER)
        tinyPoint = createTexture(objectTextures, TINY_BORDER * 6, TINY_BORDER * 2, TINY_BORDER)
        tinyPinkShuriken = createTexture(objectTextures, TINY_BORDER * 7, TINY_BORDER * 2, TINY_BORDER)
        tinySpore = createTexture(objectTextures, TINY_BORDER * 6, TINY_BORDER * 3, TINY_BORDER)
        tinyLight = createTexture(objectTextures, TINY_BORDER * 7, TINY_BORDER * 3, TINY_BORDER)


        explosionFrame1 = createTexture(objectTextures, SMALL_BORDER * 0, SMALL_BORDER * 2, SMALL_BORDER)
        explosionFrame2 = createTexture(objectTextures, SMALL_BORDER * 1, SMALL_BORDER * 2, SMALL_BORDER)
        explosionFrame3 = createTexture(objectTextures, SMALL_BORDER * 2, SMALL_BORDER * 2, SMALL_BORDER)
        explosionFrame4 = createTexture(objectTextures, SMALL_BORDER * 3, SMALL_BORDER * 2, SMALL_BORDER)
        explosionFrame5 = createTexture(objectTextures, SMALL_BORDER * 0, SMALL_BORDER * 3, SMALL_BORDER)
        explosionFrame6 = createTexture(objectTextures, SMALL_BORDER * 1, SMALL_BORDER * 3, SMALL_BORDER)
        explosionFrame7 = createTexture(objectTextures, SMALL_BORDER * 2, SMALL_BORDER * 3, SMALL_BORDER)
        explosionFrame8 = createTexture(objectTextures, SMALL_BORDER * 3, SMALL_BORDER * 3, SMALL_BORDER)

        explosionFrame9 = createTexture(objectTextures, SMALL_BORDER * 0, SMALL_BORDER * 4, SMALL_BORDER)
        explosionFrame10 = createTexture(objectTextures, SMALL_BORDER * 1, SMALL_BORDER * 4, SMALL_BORDER)
        explosionFrame11 = createTexture(objectTextures, SMALL_BORDER * 2, SMALL_BORDER * 4, SMALL_BORDER)
        explosionFrame12 = createTexture(objectTextures, SMALL_BORDER * 3, SMALL_BORDER * 4, SMALL_BORDER)
        explosionFrame13 = createTexture(objectTextures, SMALL_BORDER * 0, SMALL_BORDER * 5, SMALL_BORDER)
        explosionFrame14 = createTexture(objectTextures, SMALL_BORDER * 1, SMALL_BORDER * 5, SMALL_BORDER)
        explosionFrame15 = createTexture(objectTextures, SMALL_BORDER * 2, SMALL_BORDER * 5, SMALL_BORDER)
        explosionFrame16 = createTexture(objectTextures, SMALL_BORDER * 3, SMALL_BORDER * 5, SMALL_BORDER)

        explosionAnimation = SimpleAnimation(textures = *arrayOf(
                TextureRegion(explosionFrame1),
                TextureRegion(explosionFrame2),
                TextureRegion(explosionFrame3),
                TextureRegion(explosionFrame4),
                TextureRegion(explosionFrame5),
                TextureRegion(explosionFrame6),
                TextureRegion(explosionFrame7),
                TextureRegion(explosionFrame8),
                TextureRegion(explosionFrame9),
                TextureRegion(explosionFrame10),
                TextureRegion(explosionFrame11),
                TextureRegion(explosionFrame12),
                TextureRegion(explosionFrame13),
                TextureRegion(explosionFrame14),
                TextureRegion(explosionFrame15),
                TextureRegion(explosionFrame16)))
//        explosionFrame1 = createTexture(objectTextures, SMALL_BORDER * 3, SMALL_BORDER * 5, SMALL_BORDER)
    }

    private fun createTexture(defaultTexture: Pixmap, posX: Int, posY: Int, border: Int = DEFAULT_BORDER): Texture {
        val section = Pixmap(border, border, Pixmap.Format.RGBA8888)
        section.drawPixmap(defaultTexture, 0, 0, posX, posY, border, border)
        return Texture(section)
    }


    val bumperAnimation: PlayerAnimation
        get() {
            val landingFrame1 = TextureRegion(bumperShipMoving1)
            val landingFrame2 = TextureRegion(bumperShipLanding1)
            val landingFrame3 = TextureRegion(bumperShipLanding2)
            val animationFrame1 = TextureRegion(bumperShipMoving1)
            val animationFrame2 = TextureRegion(bumperShipMoving2)
            val animationFrame3 = TextureRegion(bumperShipMoving3)
            val animationFrame4 = TextureRegion(bumperShipMoving2)
            val movingAnimation = Animation(0.1f, animationFrame1, animationFrame2, animationFrame3, animationFrame4)
            val landingAnimation = Animation(0.3f, landingFrame1, landingFrame2, landingFrame3)
            return PlayerAnimation(movingAnimation, landingAnimation)
        }

    val raiderAnimation: PlayerAnimation
        get() {
            val landingFrame1 = TextureRegion(raiderShipMoving1)
            val landingFrame2 = TextureRegion(raiderShipLanding1)
            val landingFrame3 = TextureRegion(raiderShipLanding2)
            val animationFrame1 = TextureRegion(raiderShipMoving1)
            val animationFrame2 = TextureRegion(raiderShipMoving2)
            val animationFrame3 = TextureRegion(raiderShipMoving3)
            val animationFrame4 = TextureRegion(raiderShipMoving2)
            val movingAnimation = Animation(0.1f, animationFrame1, animationFrame2, animationFrame3, animationFrame4)
            val landingAnimation = Animation(0.3f, landingFrame1, landingFrame2, landingFrame3)
            return PlayerAnimation(movingAnimation, landingAnimation)
        }

    val speederAnimation: PlayerAnimation
        get() {
            val landingFrame1 = TextureRegion(speederShipMoving1)
            val landingFrame2 = TextureRegion(speederShipLanding1)
            val landingFrame3 = TextureRegion(speederShipLanding2)
            val animationFrame1 = TextureRegion(speederShipMoving1)
            val animationFrame2 = TextureRegion(speederShipMoving2)
            val animationFrame3 = TextureRegion(speederShipMoving3)
            val animationFrame4 = TextureRegion(speederShipMoving2)
            val movingAnimation = Animation(0.1f, animationFrame1, animationFrame2, animationFrame3, animationFrame4)
            val landingAnimation = Animation(0.3f, landingFrame1, landingFrame2, landingFrame3)
            return PlayerAnimation(movingAnimation, landingAnimation)
        }

//    fun scaleImage(image: Texture, width: Int, height: Int) {
//        val pixmap200 = Pixmap(Gdx.files.internal(image))
//        val pixmap100 = Pixmap(width.toInt(), height.toInt(), pixmap200.format)
//        pixmap100.drawPixmap(pixmap200,
//                0, 0, pixmap200.width, pixmap200.height,
//                0, 0, pixmap100.width, pixmap100.height
//        )
//        pixmap200.dispose()
//        pixmap100.dispose()
//        val texture = Texture(pixmap100)
//    }
}