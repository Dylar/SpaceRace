package de.bitb.spacerace.core.injection.modules

import dagger.Module
import dagger.Provides
import de.bitb.spacerace.core.events.commands.CommandDispender
import de.bitb.spacerace.ui.screens.editor.AddEntityDispenser
import de.bitb.spacerace.ui.screens.editor.AddEntityToMapDispenser
import de.bitb.spacerace.ui.screens.editor.EditorModeDispenser
import de.bitb.spacerace.ui.screens.editor.SelectedEntityDispenser
import de.bitb.spacerace.usecase.dispender.AttachItemDispenser
import de.bitb.spacerace.usecase.dispender.MoveItemDispenser
import de.bitb.spacerace.usecase.dispender.PlayerColorDispenser
import de.bitb.spacerace.usecase.dispender.RemoveItemDispenser
import javax.inject.Singleton

@Module
class EditorModule {

    @Provides
    @Singleton
    fun provideEditorModeDispenser(): EditorModeDispenser = EditorModeDispenser()

    @Provides
    @Singleton
    fun provideSelectedEntityDispenser(): SelectedEntityDispenser = SelectedEntityDispenser()

}
