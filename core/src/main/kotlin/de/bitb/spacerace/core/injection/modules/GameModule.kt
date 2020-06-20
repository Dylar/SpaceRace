package de.bitb.spacerace.core.injection.modules

import dagger.Module
import dagger.Provides
import de.bitb.spacerace.core.events.commands.CommandDispender
import de.bitb.spacerace.ui.screens.editor.EditorModeDispenser
import de.bitb.spacerace.ui.screens.editor.SelectedEntityDispenser
import de.bitb.spacerace.usecase.dispender.AttachItemDispenser
import de.bitb.spacerace.usecase.dispender.MoveItemDispenser
import de.bitb.spacerace.usecase.dispender.PlayerColorDispenser
import de.bitb.spacerace.usecase.dispender.RemoveItemDispenser
import javax.inject.Singleton

@Module
class GameModule {

    @Provides
    @Singleton
    fun provideCommandDispender(): CommandDispender = CommandDispender()

    @Provides
    @Singleton
    fun providePlayerColorDispender(): PlayerColorDispenser = PlayerColorDispenser()

    @Provides
    @Singleton
    fun provideAttachItemDispender(): AttachItemDispenser = AttachItemDispenser()

    @Provides
    @Singleton
    fun provideRemoveItemDispender(): RemoveItemDispenser = RemoveItemDispenser()

    @Provides
    @Singleton
    fun provideMoveItemDispender(): MoveItemDispenser = MoveItemDispenser()

    @Provides
    @Singleton
    fun provideEditorModeDispenser(): EditorModeDispenser = EditorModeDispenser()

    @Provides
    @Singleton
    fun provideSelectedEntityDispenser(): SelectedEntityDispenser = SelectedEntityDispenser()


}
