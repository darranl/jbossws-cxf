/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2006, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.wsf.stack.cxf.metadata.services;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

/**
 * Metadata model for cxf.xml 
 *
 * @author Thomas.Diesler@jboss.org
 * @author ropalka@redhat.com
 * @author alessio.soldano@jboss.com
 */
public class DDEndpoint
{
   //fields mapped to jboss-cxf.xml
   private String id;

   private String address;

   private String implementor;

   private boolean mtomEnabled;
   
   private int mtomThreshold;
   
   private boolean addressingEnabled;
   
   private boolean addressingRequired;
   
   private String addressingResponses;
   
   private boolean respectBindingEnabled;
   
   private String wsdlLocation;
   
   private QName portName;
   
   private QName serviceName;
   
   private List<String> handlers;
   
   private Map<String, String> properties;
   
   //additional fields
   private Class<?> epClass;
   
   private int counter = 0;

   public QName getPortName()
   {
      return portName;
   }

   public void setPortName(QName portName)
   {
      this.portName = portName;
   }

   public QName getServiceName()
   {
      return serviceName;
   }

   public void setServiceName(QName serviceName)
   {
      this.serviceName = serviceName;
   }
   
   public String getId()
   {
      return id;
   }

   public void setId(String id)
   {
      this.id = id;
   }

   public String getAddress()
   {
      return address;
   }

   public void setAddress(String address)
   {
      this.address = address;
   }

   public String getImplementor()
   {
      return implementor;
   }

   public void setImplementor(String implementor)
   {
      this.implementor = implementor;
   }

   public String getWsdlLocation()
   {
      return wsdlLocation;
   }

   public void setWsdlLocation(String wsdlLocation)
   {
      this.wsdlLocation = wsdlLocation;
   }

   public Class<?> getEpClass()
   {
      return epClass;
   }

   public void setEpClass(Class<?> epClass)
   {
      this.epClass = epClass;
   }

   public List<String> getHandlers()
   {
      return handlers;
   }

   public boolean isMtomEnabled()
   {
      return mtomEnabled;
   }

   public void setHandlers(List<String> handlers)
   {
      this.handlers = handlers;
   }

   public void setMtomEnabled(boolean mtomEnabled)
   {
      this.mtomEnabled = mtomEnabled;
   }

   public void setMtomThreshold(final int mtomThreshold)
   {
      this.mtomThreshold = mtomThreshold;
   }
   
   public int getMtomThreshold() {
      return this.mtomThreshold;
   }
   
   public void setRespectBindingEnabled(final boolean respectBindingEnabled) {
      this.respectBindingEnabled = respectBindingEnabled;
   }
   
   public boolean isRespectBindingEnabled() {
      return this.respectBindingEnabled;
   }
   
   
   public void setAddressingEnabled(final boolean addressingEnabled) {
      this.addressingEnabled = addressingEnabled;
   }
   
   public boolean isAddressingEnabled() {
      return this.addressingEnabled;
   }

   public void setAddressingRequired(final boolean addressingRequired) {
      this.addressingRequired = addressingRequired;
   }
   
   public boolean isAddressingRequired() {
      return this.addressingRequired;
   }
   
   public void setAddressingResponses(final String responsesTypes)
   {
      this.addressingResponses = responsesTypes;
   }
   
   public String getAddressingResponses() {
      return this.addressingResponses;
   }
   
   public Map<String, String> getProperties()
   {
      return properties;
   }

   public void setProperties(Map<String, String> properties)
   {
      this.properties = properties;
   }   
   
   public void writeTo(Writer writer) throws IOException
   {
      writer.write("<jaxws:endpoint id='" + this.id + "'");
      writer.write(" address='" + this.address + "'");
      writer.write(" implementor='" + this.implementor + "'");
      if (this.serviceName != null)
      {
         this.writeQNameElementTo("serviceName", this.serviceName, writer);
      }
      if (this.portName != null)
      {
         this.writeQNameElementTo("endpointName", this.portName, writer);
      }
      if (this.wsdlLocation != null)
      {
         writer.write(" wsdlLocation='" + this.wsdlLocation + "'");
      }
      writer.write(">");

      if (this.addressingEnabled) {
         String responses = "";
         if (this.addressingResponses != null) 
         {
            responses = " responses='" + addressingResponses + "'"; 
         }
           writer.write("<jaxws:features>");         
           writer.write("<wsa:addressing addressingRequired='" + this.addressingRequired + "'" + responses + "/>");
           writer.write("</jaxws:features>");
        }
        
        if (this.mtomEnabled)
        {
           writer.write("<jaxws:binding>");
           writer.write("<soap:soapBinding mtomEnabled='" + this.mtomEnabled + "'/>");
           writer.write("</jaxws:binding>");
        }
        
        //So far we only support standard JAXBDataBinding mtom threshold configuration
        
        if (this.mtomEnabled) 
        {
            writer.write("<jaxws:dataBinding>");
            writer.write("<bean class='org.apache.cxf.jaxb.JAXBDataBinding'>");
            writer.write("<property name='mtomEnabled' value='true'/>");
            writer.write("<property name='mtomThreshold' value='" + this.mtomThreshold + "'/>");
            writer.write("</bean>");
            writer.write("</jaxws:dataBinding>");
        }
       

      if (this.handlers != null && !this.handlers.isEmpty())
      {
         writer.write("<jaxws:handlers>");
         for (String handler : this.handlers)
         {
            writer.write("<bean class='" + handler + "'/>");
         }
         writer.write("</jaxws:handlers>");
      }
      
      if (this.properties != null && !this.properties.isEmpty())
      {
         writer.write("<jaxws:properties>");
         for (String key : this.properties.keySet())
         {
            String value = this.properties.get(key);
            if (value != null)
            {
               writer.write("<entry key='" + key + "' value='" + value + "'/>");
            }
         }
         writer.write("</jaxws:properties>");
      }

      writer.write("</jaxws:endpoint>");
   }
   
   private void writeQNameElementTo(String elementName, QName qname, Writer writer) throws IOException
   {
      String prefix = "ns" + counter++;
      writer.write(" " + elementName + "='" + prefix + ":" + qname.getLocalPart() + "'");
      writer.write(" xmlns:" + prefix + "='" + qname.getNamespaceURI() + "'");
   }

   public String toString()
   {
      StringBuilder str = new StringBuilder("Service");
      str.append("\n id=" + this.id);
      str.append("\n address=" + this.address);
      str.append("\n implementor=" + this.implementor);
      str.append("\n serviceName=" + this.serviceName);
      str.append("\n portName=" + this.portName);
      str.append("\n wsdlLocation=" + this.wsdlLocation);
      str.append("\n mtomEnabled=" + this.mtomEnabled);
      return str.toString();
   }
}
