/***********************************************************************
 *   MT4j Extension: Toolbar
 *   
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License (LGPL)
 *   as published by the Free Software Foundation, either version 3
 *   of the License, or (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the LGPL
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 ***********************************************************************/
package org.mt4jx.components.visibleComponents.widgets.toolbar;

import org.mt4j.components.MTComponent;
import org.mt4j.components.interfaces.IclickableButton;
import org.mt4j.components.visibleComponents.shapes.AbstractShape;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;

/**
 * @author Alexander Phleps
 *
 */
public class MTToolbarButtonClickAction implements IGestureEventListener {
	private AbstractShape polyButton;

	public MTToolbarButtonClickAction(AbstractShape poly) {
		this.polyButton = poly;
	}

	@Override
	public boolean processGestureEvent(MTGestureEvent g) {
		if (g instanceof TapEvent) {
			TapEvent clickEvent = (TapEvent) g;

			if (g.getTarget() instanceof MTComponent) {
				MTComponent comp = (MTComponent) g.getTarget();
				switch (clickEvent.getId()) {
				case MTGestureEvent.GESTURE_STARTED:
					if (((TapEvent) g).getTapID() == TapEvent.TAP_DOWN) {
						if (comp instanceof IclickableButton) {
							IclickableButton polyButton = (IclickableButton) g.getTarget();
							polyButton.fireActionPerformed((TapEvent) g);
						}

//						System.out.println("1");
//						try {
//						((MTToolbar2Button)comp).animate();
//						} catch(Exception e) {
//							//e.printStackTrace();							
//						}
						
						//hide any menu when invoking action on any other button
						//TODO: geht nicht wenn ListItem wegen class cast exception, au�erdem nicht sch�n auf diese weise
						try {
						if(MTLayoutContainer.visibleOne != null && ((MTToolbarButton)polyButton).getListMenu() == null)
							MTLayoutContainer.visibleOne.setVisible(false);
						} catch(Exception e) {}						
					}

					break;
				case MTGestureEvent.GESTURE_UPDATED: // NOTE: usually click
					// gesture analyzers
					// don't send gesture
					// update events
					if (((TapEvent) g).getTapID() == TapEvent.TAP_DOWN) {
						if (comp instanceof IclickableButton) {
							IclickableButton polyButton = (IclickableButton) g
									.getTarget();
							polyButton.fireActionPerformed((TapEvent) g);
						}
//						System.out.println("2");
					}

					break;
				case MTGestureEvent.GESTURE_ENDED:
					if (((TapEvent) g).getTapID() == TapEvent.TAPPED
							|| ((TapEvent) g).getTapID() == TapEvent.TAP_UP) {

						if (comp instanceof IclickableButton) {
							IclickableButton polyButton = (IclickableButton) g
									.getTarget();
							polyButton.fireActionPerformed((TapEvent) g);
						}
//						System.out.println("3");				
					}
					break;
				default:
					break;
				}

			}
		}
		return false;
	}

}
