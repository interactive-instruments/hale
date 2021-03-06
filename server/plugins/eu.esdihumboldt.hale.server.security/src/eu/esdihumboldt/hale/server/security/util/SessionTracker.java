/*
 * Copyright (c) 2012 Data Harmonisation Panel
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 *     Data Harmonisation Panel <http://www.dhpanel.eu>
 */

package eu.esdihumboldt.hale.server.security.util;

import javax.servlet.http.HttpSession;

/**
 * Tracker for sessions (possibly from multiple web applications).
 * 
 * @author Simon Templer
 */
public interface SessionTracker {

	/**
	 * Add a session.
	 * 
	 * @param session the HTTP session
	 */
	public void addSession(HttpSession session);

	/**
	 * Remove a session.
	 * 
	 * @param session the HTTP session
	 */
	public void removeSession(HttpSession session);

}
