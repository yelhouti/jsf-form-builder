/* 
 * Copyright (C) 2014 Mathias Reppe <mathias.reppe@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package at.reppeitsolutions.formbuilder.components.html.renderer.multipart;

import at.reppeitsolutions.formbuilder.components.html.renderer.formbuilder.FormFillerInternalRenderer;
import at.reppeitsolutions.formbuilder.helper.StreamUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

/**
 *
 * @author Mathias Reppe <mathias.reppe@gmail.com>
 */
public class MultipartRequest {

    private Map<String, File> files = new HashMap();
    private Map<String, String> parameters = new HashMap<>();
    private Map<String, List<String>> parameterMaps = new HashMap<>();

    public MultipartRequest(HttpServletRequest request) {
        try {
            if (request != null && request.getParts() != null) {
                for (Part part : request.getParts()) {
                    System.out.println("Name: " + part.getName());
                    if (part.getSubmittedFileName() == null) {
                        boolean isParameterMapPart = false;
                        String partValue = getValue(part.getInputStream(), part.getSize());
                        if (!parameterMaps.containsKey(part.getName())) {
                            for (Part part2 : request.getParts()) {
                                String partValue2 = getValue(part2.getInputStream(), part2.getSize());
                                if (part2.getName().equals(part.getName())
                                        && !partValue.equals(partValue2)) {
                                    isParameterMapPart = true;
                                    putParametersMapValue(part.getName(), partValue2);
                                }
                            }
                        }
                        if (!isParameterMapPart && !parameterMaps.containsKey(part.getName())) {
                            putParametersMapValue(part.getName(), partValue);
                            parameters.put(part.getName(), partValue);
                        }
                        if(isParameterMapPart) {
                            putParametersMapValue(part.getName(), partValue);
                        }
                    } else {
                        File f = new File();
                        f.setFile(StreamUtils.getBytes(part.getInputStream(), part.getSize()));
                        f.setFilename(part.getSubmittedFileName());
                        f.setFilesize(Integer.valueOf((int) part.getSize()));
                        f.setFiletype(part.getContentType());
                        files.put(part.getName(), f);
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(FormFillerInternalRenderer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServletException ex) {
            Logger.getLogger(FormFillerInternalRenderer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void putParametersMapValue(String name, String value) {
        if (parameterMaps.get(name) == null) {
            List<String> values = new ArrayList<>();
            values.add(value);
            parameterMaps.put(name, values);
        } else {
            parameterMaps.get(name).add(value);
        }
    }

    private String getValue(InputStream is, long length) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        StringBuilder value = new StringBuilder();
        char[] buffer = new char[(int) length];
        for (int l = 0; (l = reader.read(buffer)) > 0;) {
            value.append(buffer, 0, l);
        }
        return value.toString();
    }

    public Map<String, File> getFiles() {
        return files;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public Map<String, List<String>> getParameterMaps() {
        return parameterMaps;
    }

    public void setParameterMaps(Map<String, List<String>> parameterMaps) {
        this.parameterMaps = parameterMaps;
    }
}
