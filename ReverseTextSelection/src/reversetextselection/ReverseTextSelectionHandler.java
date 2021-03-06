package reversetextselection;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * This command handler moves the text caret to the other end of the selection in a
 * text editor. Repeated invocation of the command bounces the caret from one
 * end of selection to the other while keeping the selection intact. This
 * enables you to extend/contract the selection in either direction. The default
 * key binding for the Reverse Text Selection command is Alt+Shift+/. You can
 * change the key binding using Preferences > General > Keys page.
 * 
 * @author Sandip Chitale
 * 
 */
public class ReverseTextSelectionHandler extends AbstractHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (activeWorkbenchWindow != null) {
			IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
			if (activePage != null) {
				IWorkbenchPart activeEditor = activePage.getActivePart();
				StyledText styledText = null;
				if (activeEditor instanceof ITextEditor) {
					ITextEditor abstractTextEditor = (ITextEditor) activeEditor;
					Object adapter = (Control) abstractTextEditor.getAdapter(Control.class);
					if (adapter instanceof Control) {
						Control control = (Control) adapter;
						if (control instanceof StyledText) {
							styledText = (StyledText) control;

						}
					}
				} else {
					Control focusControl = activeWorkbenchWindow.getShell().getDisplay().getFocusControl();
					if (focusControl instanceof StyledText) {
						styledText = (StyledText) focusControl;
					}
				}
				if (styledText != null) {
					Point selection = styledText.getSelection();
					int caretOffset = styledText.getCaretOffset();
					if (caretOffset == selection.x) {
						styledText.setSelection(selection);
					} else {
						styledText.setSelection(selection.y, selection.x);
					}
				}
			}
		}
		return null;
	}

	public boolean isEnabled() {
		return isHandled();
	}

	public boolean isHandled() {
		IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (activeWorkbenchWindow != null) {
			IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
			if (activePage != null) {
				IWorkbenchPart activeEditor = activePage.getActivePart();
				if (activeEditor != null) {
					StyledText styledText = null;
					if (activeEditor instanceof ITextEditor) {
						ITextEditor abstractTextEditor = (ITextEditor) activeEditor;
						Object adapter = (Control) abstractTextEditor.getAdapter(Control.class);
						if (adapter instanceof Control) {
							Control control = (Control) adapter;
							if (control instanceof StyledText) {
								styledText = (StyledText) control;
							}
						}
					} else {
						Control focusControl = activeWorkbenchWindow.getShell().getDisplay().getFocusControl();
						if (focusControl instanceof StyledText) {
							styledText = (StyledText) focusControl;
						}
					}
					if (styledText != null) {
						Point selection = styledText.getSelection();
						return (selection.x != selection.y);
					}
				}
			}
		}
		return false;
	}

}
