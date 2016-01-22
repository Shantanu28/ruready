<!-- Hide script from old browsers
/*
###################################################################################
pad.js

Nava L. Livne <i><nlivne@aoce.utah.edu></i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i><olivne@aoce.utah.edu></i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-D
    
Protected by U.S. Provisional Patent U-4003, February 2006

String padding utility.
###################################################################################
*/

/**
 * String.pad(length: Integer, [substring: String = " "], [type: Integer = 0]): String
 * 
 * Returns the string with a substring padded on the left, right or both sides.
 * 
 * @param lengthamount of characters that the string must have
 * @param substringstring that will be concatenated
 * @param typespecifies the side where the concatenation will happen, where:
 * 0 = left, 1 = right and 2 = both sides
 * 
 * @return the string with a substring padded on the left, right or both sides.
 *
 * Authors:
 * + Jonas Raoni Soares Silva
 * @ http://jsfromhell.com/string/pad [v1.0]
 *
 * JSFromHell.com | http://jsfromhell.com
 * ======================================
 *
 * Name: Pad v1.0
 * URI: http://jsfromhell.com/string/pad
 * Author: jonasraoni
 * Created: 2005.11.20
 * Modified: 2005.11.20
 * Description:
 * Concatenates a substring until the determinated length is reached without loops.
 *
 * To keep yourself updated, sign up the ATOM feed at:
 * http://jsfromhell.com/atom-en.xml
 *
 * - We don't assume any responsibility for any kind of damage, direct or indirect, that can be raised by the utilization of our site or any other site that's available through hiperlinks in our site.
 * - We authorize the copy and modification of all the codes on the site, since if you keep the original author name.
 * - In case of errors on the scripts or new feature requests, please contact the author directly.
 * Example:
 * document.write(
 *	"<h2>S = ", s, "</h2>",
 *	"S.pad(20, \"[]\", 0) = ", s.pad(20, "[]", 0), "<br />",
 *	"S.pad(20, \"[====]\", 1) = ", s.pad(20, "[====]", 1), "<br />",
 *	"S.pad(20, \"~\", 2) = ", s.pad(20, "~", 2)
 * );
 *
 */
String.prototype.pad = function(l, s, t)
{
	return s || (s = " "), (l -= this.length) > 0 ? (s = new Array(Math.ceil(l / s.length)
		+ 1).join(s)).substr(0, t = !t ? l : t == 1 ? 0 : Math.ceil(l / 2))
		+ this + s.substr(0, l - t) : this;
};
		
// End hiding script from old browsers -->

