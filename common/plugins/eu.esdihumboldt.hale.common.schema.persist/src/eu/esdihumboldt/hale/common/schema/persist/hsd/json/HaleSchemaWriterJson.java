/*
 * Copyright (c) 2017 wetransform GmbH
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
 *     wetransform GmbH <http://www.wetransform.to>
 */

package eu.esdihumboldt.hale.common.schema.persist.hsd.json;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import eu.esdihumboldt.hale.common.core.io.IOProviderConfigurationException;
import eu.esdihumboldt.hale.common.core.io.ProgressIndicator;
import eu.esdihumboldt.hale.common.core.io.report.IOReport;
import eu.esdihumboldt.hale.common.core.io.report.IOReporter;
import eu.esdihumboldt.hale.common.core.io.report.impl.IOMessageImpl;
import eu.esdihumboldt.hale.common.schema.io.impl.AbstractSchemaWriter;
import eu.esdihumboldt.util.groovy.json.JsonStreamBuilder;

/**
 * Writes the HALE schema model to XML.
 * 
 * @author Simon Templer
 */
public class HaleSchemaWriterJson extends AbstractSchemaWriter {

	@Override
	public boolean isCancelable() {
		return false;
	}

	@Override
	protected IOReport execute(ProgressIndicator progress, IOReporter reporter)
			throws IOProviderConfigurationException, IOException {
		progress.begin("Save schema", ProgressIndicator.UNKNOWN);
		try (OutputStream out = getTarget().getOutput();
				Writer writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
			// create DOM
			JsonStreamBuilder builder = new JsonStreamBuilder(writer, true);
			new SchemaToJson().schemasToJson(builder, getSchemas().getSchemas(), null);

			reporter.setSuccess(true);
		} catch (Exception e) {
			// XXXXXXXXXXXXXXXXXX
			e.printStackTrace();

			reporter.error(new IOMessageImpl(e.getMessage(), e));
			reporter.setSuccess(false);
		} finally {
			progress.end();
		}
		return reporter;
	}

	@Override
	protected String getDefaultTypeName() {
		return "HALE Schema Definition (JSON)";
	}

}
