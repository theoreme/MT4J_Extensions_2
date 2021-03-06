 /**
 * This material was prepared as an account of work sponsored by an agency of the United States Government.<br>
 * Neither the United States Government nor the United States Department of Energy, nor any of their employees,<br> 
 * nor any of their contractors, subcontractors or their employees, makes any warranty, express or implied, or<br>
 * assumes any legal liability or responsibility for the accuracy, completeness, or usefulness or any information,<br> 
 * apparatus, product, or process disclosed, or represents that its use would not infringe privately owned rights.
 */
package org.mt4jx.components.visibleComponents.font.fontFactories;

import org.mt4j.components.visibleComponents.font.fontFactories.SvgFontFactory;
import org.mt4j.util.xml.XmlHandler;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 * <p>Extension of SvgFontFactory which can extract the font name
 * from a file without instantiation of the font.
 * </p>
 * 
 * @author R.Scarberry
 */
public class EnhancedSvgFontFactory extends SvgFontFactory 
  implements IEnhancedFontFactory {
	
	public EnhancedSvgFontFactory() {  }

	/**
	 * Quickly extracts the name of the font from a properly-formatted
	 * svg font file.
	 */
	public String extractFontName(String svgFontFileName) {
		
	    // Holder for the extracted name.  The nested handler needs
	    // a final in which to store the results.  
		final String[] nameHolder = new String[1];
		
		try {
			
			XmlHandler.getInstance().saxParse(svgFontFileName, new DefaultHandler() {
		
			    // Set to false when the name has been found.
				boolean stillLooking = true;
				
				@Override
				public void startElement(String uri, String localName, String qName, Attributes attributes){
					if (stillLooking) {
						if(qName.equalsIgnoreCase("font") || qName.equalsIgnoreCase("font-face")) {
							for ( int i = 0; i < attributes.getLength(); i++ ){ 
								String currentAttributeName = attributes.getQName(i);
								if (currentAttributeName.equalsIgnoreCase("id")){
									nameHolder[0] = attributes.getValue(i);
									stillLooking = false;
								}
							}
						}
					}
				}
				
			});
			
		} catch (Exception e) {
			// Don't worry about it -- this simply means it probably wasn't a font
		    // file.  Null will be returned.
		}
		
		return nameHolder[0];
	}

}
