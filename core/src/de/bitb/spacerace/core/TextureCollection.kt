package de.bitb.spacerace.core

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Skin


object TextureCollection {
    private const val imageSize = 64

    val skin = Skin(Gdx.files.internal("uiskin.json"))

    val guiBackground: Texture
    val gameoverBackground: Texture
    val gameBackground: Texture

    val blackhole: Texture
    val fallingStar: Texture

    val brownField: Texture
    val grayField: Texture
    val blueField: Texture
    val redField: Texture
    val greenField: Texture
    val yellowField: Texture

    val ship1: Texture
    val ship2: Texture

    val fieldItemShop: Texture

    val slowMine: Texture


    init {
        val allTexture = Pixmap(Gdx.files.internal("objects.png"))

        guiBackground = Texture("background/bg_silver.png")
        gameoverBackground = Texture("background/bg_gameover.jpg")
        gameBackground = Texture("background/bg_star.png")
        gameBackground.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat)

        var section = Pixmap(imageSize, imageSize, Pixmap.Format.RGBA8888)
        section.drawPixmap(allTexture, imageSize * 0, imageSize * 0, imageSize * 2, imageSize * 0, imageSize, imageSize)
        blueField = Texture(section)
        section.drawPixmap(allTexture, imageSize * 0, imageSize * 0, imageSize, imageSize, imageSize, imageSize)
        redField = Texture(section)
        section.drawPixmap(allTexture, imageSize * 0, imageSize * 0, imageSize * 0, imageSize, imageSize, imageSize)
        greenField = Texture(section)
        section.drawPixmap(allTexture, imageSize * 0, imageSize * 0, imageSize * 2, imageSize, imageSize, imageSize)
        yellowField = Texture(section)
        section.drawPixmap(allTexture, imageSize * 0, imageSize * 0, imageSize, imageSize * 0, imageSize, imageSize)
        grayField = Texture(section)
        section.drawPixmap(allTexture, imageSize * 0, imageSize * 0, imageSize * 0, imageSize * 0, imageSize, imageSize)
        brownField = Texture(section)

        section = Pixmap(imageSize, imageSize, Pixmap.Format.RGBA8888)
        section.drawPixmap(allTexture, imageSize * 0, imageSize * 0, imageSize * 3, imageSize * 0, imageSize, imageSize)
        ship1 = Texture(section)
        section = Pixmap(imageSize, imageSize, Pixmap.Format.RGBA8888)
        section.drawPixmap(allTexture, imageSize * 0, imageSize * 0, imageSize * 3, imageSize, imageSize, imageSize)
        ship2 = Texture(section)

        blackhole = Texture("objects/blackhole.png")
        fallingStar = Texture("objects/falling_star.png")
        slowMine = Texture("objects/item_mine.png")
        fieldItemShop = Texture("objects/field_item_shop.png")

    }
}