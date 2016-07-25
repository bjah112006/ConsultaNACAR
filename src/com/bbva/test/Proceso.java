package com.bbva.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.axis.AxisFault;

import com.grupobbva.pe.tat.ents.body.consultaConFiltros.RangoSolicitud;
import com.grupobbva.pe.tat.ents.body.entrega.Entrega;
import com.grupobbva.pe.tat.ents.header.RequestHeader;
import com.grupobbva.pe.tat.service.message.ConsultaConFiltrosRequest;
import com.grupobbva.pe.tat.service.message.ConsultaConFiltrosResponse;
import com.grupobbva.pe.tat.service.message.ConsultaPorDocumentoRequest;
import com.grupobbva.pe.tat.service.message.ConsultaPorDocumentoResponse;
import com.grupobbva.pe.tat.service.message.EntregaTarjetasWSProxy;
import com.grupobbva.pe.tat.service.message.EntregaTarjetasWS_HttpBindingStub;
import com.grupobbva.pe.tat.service.message.EntregaTarjetasWS_Service;
import com.grupobbva.pe.tat.service.message.EntregaTarjetasWS_ServiceLocator;

public class Proceso {

	public int tiporesult=0;
	
	public java.lang.String getWsdl() throws java.io.IOException{
    	java.lang.String raiz = null;
    	java.util.Properties configProperties = new java.util.Properties();
    	java.io.FileInputStream file;
	    java.lang.String path = "./config.properties";
	    file = new java.io.FileInputStream(path);
	    configProperties.load(file);
	    file.close();
	    raiz = configProperties.getProperty("wsdl");
    	return raiz;
    }
	
	public ConsultaPorDocumentoResponse getResultDoc(String[] datosent) throws AxisFault{
		
		//ConsultaPorDocumentoResponse resultado = new ConsultaPorDocumentoResponse();
		String cod_resp=null;
			
			com.grupobbva.pe.tat.ents.body.consultaPorDocumento.ConsultaPorDocumentoRequest bodreqdoc = new com.grupobbva.pe.tat.ents.body.consultaPorDocumento.ConsultaPorDocumentoRequest();
			bodreqdoc.setTipoDocumento(datosent[3].toString());
			bodreqdoc.setNumeroDocumento(datosent[4].toString());
			
			RequestHeader reqhead = new RequestHeader();
			reqhead.setUsuario(datosent[1].toString());
			reqhead.setOficina(datosent[2].toString());
			
			ConsultaPorDocumentoRequest servreq = new ConsultaPorDocumentoRequest();
			servreq.setRefConsultaPorDocumentoRequest(bodreqdoc);
			servreq.setRefRequestHeader(reqhead);
			
			EntregaTarjetasWS_Service serv = new EntregaTarjetasWS_ServiceLocator();
			
			ConsultaPorDocumentoResponse servres = null;
			com.grupobbva.pe.tat.ents.body.consultaPorDocumento.ConsultaPorDocumentoResponse bodresdoc = new com.grupobbva.pe.tat.ents.body.consultaPorDocumento.ConsultaPorDocumentoResponse();
			
			java.net.URL url;
			try {
				url = new java.net.URL(serv.getEntregaTarjetasWSAddress());
				System.out.println("URI wsdl: "+url.toString());
				EntregaTarjetasWS_HttpBindingStub entregaserv = new EntregaTarjetasWS_HttpBindingStub(url,serv);
				servres = entregaserv.consultaPorDocumento(servreq);
				
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				tiporesult=1;
			}
			return servres;
		
		
		
	}
	
	public int gettiporesult(){
		return tiporesult;
	}
	
	public ConsultaConFiltrosResponse getResultAvan(String[] datosent){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-01");
		Date fsoldesde = null;
		Date fsolhasta = null;
		Date fecha = null;
		String fec = null;
		
		com.grupobbva.pe.tat.ents.body.consultaConFiltros.RangoSolicitud fsolrange = new RangoSolicitud();
		
		if(datosent[7].equals("") && (datosent[8]==null || datosent[8].equals(""))){
			System.out.println("2 en blanco");
			fecha=new Date();
			try {
				fec = sdf1.format(fecha);
				System.out.println("fecha inicio: "+ fec);
				fsoldesde = sdf1.parse(fec);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			fecha=new Date();
			try {
				fec = sdf.format(fecha);
				System.out.println("fecha fin: "+ fec);
				fsolhasta = sdf.parse(fec);
				
				//Agregado
				
				//////////
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			if(datosent[7].equals("") && (datosent[8]!=null || !datosent[8].equals(""))){
				System.out.println("inicio en blanco");
				try {
					fsolhasta = sdf.parse(datosent[8]);
					Calendar c = Calendar.getInstance();
					c.setTime(fsolhasta);
					String anio=String.valueOf(c.get(Calendar.YEAR));
					int mess=c.get(Calendar.MONTH);
					mess=mess+1;
					String mes=null;
					if(mess>9){
						mes=String.valueOf(mess);
					}else{
						mes="0"+String.valueOf(mess);
					}
					String fech = anio+"-"+mes+"-01";
					fsoldesde = sdf1.parse(fech);
					
					System.out.println("inicio: "+ fsoldesde.toString());
					System.out.println("fin: "+ fsolhasta.toString());
					
					//Agregado
					
					fsolrange.setFin(fsolhasta);
					//////////
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(!datosent[7].equals("") && (datosent[8]==null || datosent[8].equals(""))){
				System.out.println("fin en blanco");
				fecha=new Date();
				try {
					fsoldesde = sdf.parse(datosent[7]);
					fec = sdf.format(fecha);
					fsolhasta = sdf.parse(fec);
					
					System.out.println("inicio: "+ fsoldesde.toString());
					System.out.println("fin: "+ fsolhasta.toString());
					
					//Agregado
					fsolrange.setInicio(fsoldesde);
					
					//////////
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(!datosent[7].equals("") && (datosent[8]!=null && !datosent[8].equals(""))){
				System.out.println("ninguno en blanco");
				try {
					fsoldesde = sdf.parse(datosent[7]);
					fsolhasta = sdf.parse(datosent[8]);
					
					System.out.println("inicio: "+ fsoldesde.toString());
					System.out.println("fin: "+ fsolhasta.toString());
					
					//Agregado
					fsolrange.setInicio(fsoldesde);
					fsolrange.setFin(fsolhasta);
					//////////
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		
		/*
		fsolrange.setInicio(fsoldesde);
		fsolrange.setFin(fsolhasta);
		*/
		
		com.grupobbva.pe.tat.ents.body.consultaConFiltros.ConsultaConFiltrosRequest bodreqava = new com.grupobbva.pe.tat.ents.body.consultaConFiltros.ConsultaConFiltrosRequest();
		
		bodreqava.setCodigoOficina(datosent[5].toString());
		bodreqava.setCodigoEstado(datosent[6].toString());
		bodreqava.setRangoSolicitud(fsolrange);
		
		RequestHeader reqhead = new RequestHeader();
		reqhead.setUsuario(datosent[1].toString());
		reqhead.setOficina(datosent[2].toString());
		
		ConsultaConFiltrosRequest servreq = new ConsultaConFiltrosRequest();
		servreq.setRefConsultaConFiltrosRequest(bodreqava);
		servreq.setRefRequestHeader(reqhead);
		
		EntregaTarjetasWS_Service serv = new EntregaTarjetasWS_ServiceLocator();
		
		java.net.URL url;
		
		ConsultaConFiltrosResponse servres=null;
		
		try {
			url = new java.net.URL(serv.getEntregaTarjetasWSAddress());
			System.out.println("URI wsdl: "+url.toString());
			EntregaTarjetasWS_HttpBindingStub entregaserv = new EntregaTarjetasWS_HttpBindingStub(url,serv);
			servres = entregaserv.consultaConFiltros(servreq);
			
			//Integer i=servres.getRefConsultaConFiltrosResponse().getTotal();
			
			/*
			if(i!=0){
				resultado = new Entrega[i];
				resultado=servres.getRefConsultaConFiltrosResponse().getListadoEntregas();
			}
			*/
			
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			tiporesult=1;
		}
				
		return servres;
		
		
	}
	
	// Método para imprimir los registros teniendo en cuenta la longitud especificada en el archivo properties //
	public String cadena(String nombrecampo, String texto) throws IOException{
		
		String cadena=null;
		int numero;
		Properties configProperties = new Properties();
	    FileInputStream file;
	    String path = "./config.properties";
	    file = new FileInputStream(path);
		configProperties.load(file);
		file.close();
		numero = Integer.parseInt(configProperties.getProperty(nombrecampo));
	    if (texto.length()>numero){
	    	cadena=texto.substring(0,numero);
	    }else{
	    	int a = numero-texto.length();
	    	cadena=texto;
	    	if(a!=0){
	    		for(int i=0;i<a;i++){
	    			cadena=cadena+" ";
	    		}
	    	}	
	    }
		return cadena;
	}
	
	public ConsultaConFiltrosResponse ordenarLista (ConsultaConFiltrosResponse resultado){
		Entrega aux;
		int n=resultado.getRefConsultaConFiltrosResponse().getListadoEntregas().length;
		for(int i=1;i<n;i++){
			for(int j=0;j<n-i;j++){
				if(Integer.parseInt(resultado.getRefConsultaConFiltrosResponse().getListadoEntregas()[j].getDatosEntrega().getCodEstadoEntrega())<Integer.parseInt(resultado.getRefConsultaConFiltrosResponse().getListadoEntregas()[j+1].getDatosEntrega().getCodEstadoEntrega())){
					aux = resultado.getRefConsultaConFiltrosResponse().getListadoEntregas()[j];
					resultado.getRefConsultaConFiltrosResponse().getListadoEntregas()[j] = resultado.getRefConsultaConFiltrosResponse().getListadoEntregas()[j+1];
					resultado.getRefConsultaConFiltrosResponse().getListadoEntregas()[j+1] = aux;
				}
			}
		}
		
		return resultado;
	}
	
	public Entrega[] filtrarLista (ConsultaConFiltrosResponse resultado){
		int n=resultado.getRefConsultaConFiltrosResponse().getListadoEntregas().length;
		Entrega[] aux = new Entrega[n];
		int k=0;
		for(int i=0;i<n;i++){
			if(resultado.getRefConsultaConFiltrosResponse().getListadoEntregas()[i].getDatosEntrega().getCodEstadoEntrega().equals("12") || resultado.getRefConsultaConFiltrosResponse().getListadoEntregas()[i].getDatosEntrega().getCodEstadoEntrega().equals("13")){
				aux[k]=resultado.getRefConsultaConFiltrosResponse().getListadoEntregas()[i];
				k=k+1;
				
			}
		}
		Entrega[] aux2 = new Entrega[k];
		
		for(int j=0;j<k;j++){
			aux2[j]=aux[j];
		}
		
		return aux2;
	}
	
	
}
