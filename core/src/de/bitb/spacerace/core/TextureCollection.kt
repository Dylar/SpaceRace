package de.bitb.spacerace.core

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture


object TextureCollection {
    private const val imageSize = 64

    val background: Texture

    val brownField: Texture
    val grayField: Texture
    val blueField: Texture
    val redField: Texture
    val greenField: Texture
    val yellowField: Texture

    val ship1: Texture
    val ship2: Texture

    init {
        val allTexture = Pixmap(Gdx.files.internal("objects.png"))

        background = Texture(Gdx.files.internal("bg_silver.png"))

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

    }
}