/*
 * TeleStax, Open Source Cloud Communications
 * Copyright 2011-2015, Telestax Inc and individual contributors
 * by the @authors tag.
 *
 * This program is free software: you can redistribute it and/or modify
 * under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package org.mobicents.io.undertow.servlet.api;

import org.mobicents.io.undertow.servlet.core.ConvergedServletContainerImpl;

import io.undertow.servlet.api.ServletContainer;
/**
 * Factory class for creating org.mobicents.io.undertow.servlet.core.ConvergedServletContainerImpl
 *
 * @author kakonyi.istvan@alerant.hu
 */
public interface ConvergedServletContainer extends ServletContainer{

    public static class ConvergedFactory {

        public static ConvergedServletContainer newInstance() {
            return new ConvergedServletContainerImpl();
        }
    }
}
