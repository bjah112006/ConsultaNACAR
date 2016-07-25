package com.bbva.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Properties;

import com.grupobbva.pe.tat.ents.body.entrega.Entrega;
import com.grupobbva.pe.tat.service.message.ConsultaConFiltrosResponse;
import com.grupobbva.pe.tat.service.message.ConsultaPorDocumentoResponse;

public class Salida {

	public String getRutaOutputFile(String ruta) throws IOException{
		String rutaofile = null;
	    String ofile = null;

	    //to load application's properties, we use this class
	    Properties configProperties = new Properties();

	    FileInputStream file;

	    //the base folder is ./, the root of the main.properties file  
	    String path = "./config.properties";

	    //load the file handle for main.properties
	    file = new FileInputStream(path);

	    //load all the properties from this file
	    configProperties.load(file);

	    //we have loaded the properties, so close the file handle
	    file.close();

	    //retrieve the property we are intrested, the app.version
	    ofile = configProperties.getProperty("ofile");
	    
	    rutaofile = ruta+ofile;
	    
	    return rutaofile;
	}
	
	public void createOutputFile(String rutaofile){
		
		File archivo = new File(rutaofile);
		
		try {
			  // A partir del objeto File creamos el fichero físicamente
			  if (archivo.createNewFile()){
			    System.out.println("El fichero se ha creado correctamente");
			  }else{
			    archivo.delete();
			  	archivo.createNewFile();
				System.out.println("fichero Output existía. Se borró y creó uno nuevo");}
			} catch (IOException ioe) {
			  ioe.printStackTrace();
			}
		
	}
	
	
	
	public void saveDataAvanzada(String rutaofile, ConsultaConFiltrosResponse resultado){
		
		FileWriter fichero = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Proceso proces =new Proceso();
		Locale locale  = new Locale("en", "UK");
		String pattern = "#,###,###.00";

		DecimalFormat decformat = (DecimalFormat)
		        NumberFormat.getNumberInstance(locale);
		decformat.applyPattern(pattern);
		 
		try {

			fichero = new FileWriter(rutaofile);
			//BufferedWriter bw = new BufferedWriter(fichero);
			int i = resultado.getRefConsultaConFiltrosResponse().getListadoEntregas().length;
			
			// Escribimos linea a linea en el fichero
			if(i!=0){
				
				for (int n=0; n<i; n++) {
					//bw.newLine();
					if (resultado.getRefConsultaConFiltrosResponse().getListadoEntregas()[n].getDatosPersona().getTipoDocumento()==null){
						fichero.write(proces.cadena("tipoDocumento", ""));
					}else{
						fichero.write(proces.cadena("tipoDocumento", resultado.getRefConsultaConFiltrosResponse().getListadoEntregas()[n].getDatosPersona().getTipoDocumento()));
					}
					//fichero.write("; ");
					if (resultado.getRefConsultaConFiltrosResponse().getListadoEntregas()[n].getDatosPersona().getNumeroDocumento()==null){
						fichero.write(proces.cadena("numeroDocumento", ""));
					}else{
						fichero.write(proces.cadena("numeroDocumento", resultado.getRefConsultaConFiltrosResponse().getListadoEntregas()[n].getDatosPersona().getNumeroDocumento()));
					}
					//fichero.write("; ");
					if (resultado.getRefConsultaConFiltrosResponse().getListadoEntregas()[n].getDatosPersona().getNombreCompletoCliente()==null){
						fichero.write(proces.cadena("nombreCompletoCliente", ""));
					}else{
						fichero.write(proces.cadena("nombreCompletoCliente", resultado.getRefConsultaConFiltrosResponse().getListadoEntregas()[n].getDatosPersona().getNombreCompletoCliente()));
					}
					//fichero.write("; ");
					if (resultado.getRefConsultaConFiltrosResponse().getListadoEntregas()[n].getDatosEntrega().getIdEntrega().toString()==null){
						fichero.write(proces.cadena("idEntrega", ""));
					}else{
						fichero.write(proces.cadena("idEntrega", resultado.getRefConsultaConFiltrosResponse().getListadoEntregas()[n].getDatosEntrega().getIdEntrega().toString()));
					}
					//fichero.write("; ");
					if (resultado.getRefConsultaConFiltrosResponse().getListadoEntregas()[n].getDatosEntrega().getCodEstadoEntrega()==null){
						fichero.write(proces.cadena("codEstadoEntrega", ""));
					}else{
						fichero.write(proces.cadena("codEstadoEntrega", resultado.getRefConsultaConFiltrosResponse().getListadoEntregas()[n].getDatosEntrega().getCodEstadoEntrega()));
					}
					//fichero.write("; ");
					if (resultado.getRefConsultaConFiltrosResponse().getListadoEntregas()[n].getDatosEntrega().getDescEstadoEntrega()==null){
						fichero.write(proces.cadena("descEstadoEntrega", ""));
					}else{
						fichero.write(proces.cadena("descEstadoEntrega", resultado.getRefConsultaConFiltrosResponse().getListadoEntregas()[n].getDatosEntrega().getDescEstadoEntrega()));
					}
					//fichero.write("; ");
					if (resultado.getRefConsultaConFiltrosResponse().getListadoEntregas()[n].getDatosEntrega().getCodEstadoAnterior()==null){
						fichero.write(proces.cadena("codEstadoAnterior", ""));
						//fichero.write("; ");
						fichero.write(proces.cadena("descEstadoAnterior", ""));
						//fichero.write("; ");
						fichero.write(proces.cadena("fecEstadoAnterior", ""));
					}else{
						fichero.write(proces.cadena("codEstadoAnterior", resultado.getRefConsultaConFiltrosResponse().getListadoEntregas()[n].getDatosEntrega().getCodEstadoAnterior()));
						//fichero.write("; ");
						fichero.write(proces.cadena("descEstadoAnterior", resultado.getRefConsultaConFiltrosResponse().getListadoEntregas()[n].getDatosEntrega().getDescEstadoAnterior()));
						//fichero.write("; ");
						fichero.write(proces.cadena("fecEstadoAnterior", sdf.format(resultado.getRefConsultaConFiltrosResponse().getListadoEntregas()[n].getDatosEntrega().getFecEstadoAnterior())));
					}
					//fichero.write("; ");
					if (resultado.getRefConsultaConFiltrosResponse().getListadoEntregas()[n].getDatosEntrega().getCodOficina()==null){
						fichero.write(proces.cadena("codOficina", ""));
						//fichero.write("; ");
						fichero.write(proces.cadena("descOficina", ""));
						//fichero.write("; ");
						fichero.write(proces.cadena("dirOficina", ""));
					}else{
						fichero.write(proces.cadena("codOficina", resultado.getRefConsultaConFiltrosResponse().getListadoEntregas()[n].getDatosEntrega().getCodOficina()));
						//fichero.write("; ");
						fichero.write(proces.cadena("descOficina", resultado.getRefConsultaConFiltrosResponse().getListadoEntregas()[n].getDatosEntrega().getDescOficina()));
						//fichero.write("; ");
						fichero.write(proces.cadena("dirOficina", resultado.getRefConsultaConFiltrosResponse().getListadoEntregas()[n].getDatosEntrega().getDirOficina()));
					}
					//fichero.write("; ");
					fichero.write(proces.cadena("fecSolicitud", sdf.format(resultado.getRefConsultaConFiltrosResponse().getListadoEntregas()[n].getDatosEntrega().getFecSolicitud())));
					//fichero.write("; ");
					fichero.write(proces.cadena("fecEntrega", sdf.format(resultado.getRefConsultaConFiltrosResponse().getListadoEntregas()[n].getDatosEntrega().getFecEntrega())));
					//fichero.write("; ");
					fichero.write(proces.cadena("tarjeta", resultado.getRefConsultaConFiltrosResponse().getListadoEntregas()[n].getDatosOferta().getTarjeta()));
					//fichero.write("; ");
					fichero.write(proces.cadena("tipoProducto", resultado.getRefConsultaConFiltrosResponse().getListadoEntregas()[n].getDatosOferta().getTipoProducto()));
					//fichero.write("; ");
					fichero.write(proces.cadena("descProducto", resultado.getRefConsultaConFiltrosResponse().getListadoEntregas()[n].getDatosOferta().getDescProducto()));
					//fichero.write("; ");
					fichero.write(proces.cadena("primeros4Digitos", resultado.getRefConsultaConFiltrosResponse().getListadoEntregas()[n].getDatosOferta().getPrimeros4Digitos()));
					//fichero.write("; ");
					fichero.write(proces.cadena("ultimos4Digitos", resultado.getRefConsultaConFiltrosResponse().getListadoEntregas()[n].getDatosOferta().getUltimos4Digitos()));
					//fichero.write("; ");
					fichero.write(proces.cadena("contrato", resultado.getRefConsultaConFiltrosResponse().getListadoEntregas()[n].getDatosOferta().getContrato()));
					//fichero.write("; ");
					fichero.write(proces.cadena("monto", decformat.format(resultado.getRefConsultaConFiltrosResponse().getListadoEntregas()[n].getDatosOferta().getMonto())));
					//fichero.write("; ");
					fichero.write(proces.cadena("diaPago", resultado.getRefConsultaConFiltrosResponse().getListadoEntregas()[n].getDatosOferta().getDiaPago()));
					//fichero.write("; ");
					fichero.write(proces.cadena("flagAcptoTratDat", resultado.getRefConsultaConFiltrosResponse().getListadoEntregas()[n].getDatosOferta().getFlagAcptoTratDat().toString()));
					
					if(resultado.getRefConsultaConFiltrosResponse().getListadoEntregas()[n].getDatosPersona().getTelefono()==null){
						fichero.write(proces.cadena("telefono", ""));
					}else{
						fichero.write(proces.cadena("telefono", resultado.getRefConsultaConFiltrosResponse().getListadoEntregas()[n].getDatosPersona().getTelefono()));
					}
					
					if(resultado.getRefConsultaConFiltrosResponse().getListadoEntregas()[n].getDatosPersona().getDireccion()==null){
						fichero.write(proces.cadena("dircliente", ""));
					}else{
						fichero.write(proces.cadena("dircliente", resultado.getRefConsultaConFiltrosResponse().getListadoEntregas()[n].getDatosPersona().getDireccion()));
					}
					
					if(resultado.getRefConsultaConFiltrosResponse().getListadoEntregas()[n].getDatosPersona().getExonerarVerifDomicilio()==null){
						fichero.write(proces.cadena("exoVD", ""));
					}else{
						String exonVD = null;
						Boolean val = resultado.getRefConsultaConFiltrosResponse().getListadoEntregas()[n].getDatosPersona().getExonerarVerifDomicilio();
						if(val.booleanValue()==true){
							exonVD="1";
						}else{
							exonVD="0";
						}
						fichero.write(proces.cadena("exoVD", exonVD));
					}
					
					fichero.write("\r\n");
				}
				
			}
			

			fichero.close();

		} catch (Exception ex) {
			System.out.println("Mensaje de la excepción: " + ex.getMessage());
		}
		//System.out.println("Se guardaron los datos en el archivo cuya ruta es: \r\n"+rutaofile);
	}
	
	public void saveDataDocumento(String rutaofile, ConsultaPorDocumentoResponse resultado){
		
		if(resultado.getRefConsultaPorDocumentoResponse().getEntrega().getDatosEntrega().getCodEstadoEntrega().equals("13") || resultado.getRefConsultaPorDocumentoResponse().getEntrega().getDatosEntrega().getCodEstadoEntrega().equals("12")){
			FileWriter fichero = null;
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Proceso proces = new Proceso();
			Locale locale  = new Locale("en", "UK");
			String pattern = "#,###,###.00";

			DecimalFormat decformat = (DecimalFormat)
			        NumberFormat.getNumberInstance(locale);
			decformat.applyPattern(pattern);
			
			try {

				fichero = new FileWriter(rutaofile);
				
				if (resultado.getRefConsultaPorDocumentoResponse().getEntrega().getDatosPersona().getTipoDocumento()==null){
					fichero.write(proces.cadena("tipoDocumento",""));
				}else{
					fichero.write(proces.cadena("tipoDocumento", resultado.getRefConsultaPorDocumentoResponse().getEntrega().getDatosPersona().getTipoDocumento()));
				}
				//fichero.write("; ");
				if (resultado.getRefConsultaPorDocumentoResponse().getEntrega().getDatosPersona().getNumeroDocumento()==null){
					fichero.write(proces.cadena("numeroDocumento",""));
				}else{
					fichero.write(proces.cadena("numeroDocumento", resultado.getRefConsultaPorDocumentoResponse().getEntrega().getDatosPersona().getNumeroDocumento()));
				}
				//fichero.write("; ");
				if (resultado.getRefConsultaPorDocumentoResponse().getEntrega().getDatosPersona().getNombreCompletoCliente()==null){
					fichero.write(proces.cadena("nombreCompletoCliente",""));
				}else{
					fichero.write(proces.cadena("nombreCompletoCliente", resultado.getRefConsultaPorDocumentoResponse().getEntrega().getDatosPersona().getNombreCompletoCliente()));
				}
				//fichero.write("; ");
				if (resultado.getRefConsultaPorDocumentoResponse().getEntrega().getDatosEntrega().getIdEntrega().toString()==null){
					fichero.write(proces.cadena("idEntrega",""));
				}else{
					fichero.write(proces.cadena("idEntrega", resultado.getRefConsultaPorDocumentoResponse().getEntrega().getDatosEntrega().getIdEntrega().toString()));
				}
				//fichero.write("; ");
				if (resultado.getRefConsultaPorDocumentoResponse().getEntrega().getDatosEntrega().getCodEstadoEntrega()==null){
					fichero.write(proces.cadena("codEstadoEntrega",""));
				}else{
					fichero.write(proces.cadena("codEstadoEntrega", resultado.getRefConsultaPorDocumentoResponse().getEntrega().getDatosEntrega().getCodEstadoEntrega()));
				}
				//fichero.write("; ");
				if (resultado.getRefConsultaPorDocumentoResponse().getEntrega().getDatosEntrega().getDescEstadoEntrega()==null){
					fichero.write(proces.cadena("descEstadoEntrega",""));
				}else{
					fichero.write(proces.cadena("descEstadoEntrega", resultado.getRefConsultaPorDocumentoResponse().getEntrega().getDatosEntrega().getDescEstadoEntrega()));
				}
				//fichero.write("; ");
				if (resultado.getRefConsultaPorDocumentoResponse().getEntrega().getDatosEntrega().getCodEstadoAnterior()==null){
					//System.out.println("si es nulo /cod estado anterior: "+ resultado.getRefConsultaPorDocumentoResponse().getEntrega().getDatosEntrega().getCodEstadoAnterior());
					fichero.write(proces.cadena("codEstadoAnterior",""));
					//fichero.write("; ");
					fichero.write(proces.cadena("descEstadoAnterior",""));
					//fichero.write("; ");
					fichero.write(proces.cadena("fecEstadoAnterior",""));
				}else{
					//System.out.println("else /cod estado anterior: "+ resultado.getRefConsultaPorDocumentoResponse().getEntrega().getDatosEntrega().getCodEstadoAnterior());
					fichero.write(proces.cadena("codEstadoAnterior", resultado.getRefConsultaPorDocumentoResponse().getEntrega().getDatosEntrega().getCodEstadoAnterior()));
					//fichero.write("; ");
					fichero.write(proces.cadena("descEstadoAnterior", resultado.getRefConsultaPorDocumentoResponse().getEntrega().getDatosEntrega().getDescEstadoAnterior()));
					//fichero.write("; ");
					fichero.write(proces.cadena("fecEstadoAnterior", sdf.format(resultado.getRefConsultaPorDocumentoResponse().getEntrega().getDatosEntrega().getFecEstadoAnterior())));
				}
				//fichero.write("; ");
				if (resultado.getRefConsultaPorDocumentoResponse().getEntrega().getDatosEntrega().getCodOficina()==null){
					//System.out.println("si es nulo /cod oficina: "+ resultado.getRefConsultaPorDocumentoResponse().getEntrega().getDatosEntrega().getCodOficina());
					fichero.write(proces.cadena("codOficina",""));
					//fichero.write("; ");
					fichero.write(proces.cadena("descOficina",""));
					//fichero.write("; ");
					fichero.write(proces.cadena("dirOficina",""));
				}else{
					//System.out.println("else /cod oficina: "+ resultado.getRefConsultaPorDocumentoResponse().getEntrega().getDatosEntrega().getCodOficina());
					fichero.write(proces.cadena("codOficina", resultado.getRefConsultaPorDocumentoResponse().getEntrega().getDatosEntrega().getCodOficina()));
					//fichero.write("; ");
					fichero.write(proces.cadena("descOficina", resultado.getRefConsultaPorDocumentoResponse().getEntrega().getDatosEntrega().getDescOficina()));
					//fichero.write("; ");
					fichero.write(proces.cadena("dirOficina", resultado.getRefConsultaPorDocumentoResponse().getEntrega().getDatosEntrega().getDirOficina()));
				}
				//fichero.write("; ");
				fichero.write(proces.cadena("fecSolicitud", sdf.format(resultado.getRefConsultaPorDocumentoResponse().getEntrega().getDatosEntrega().getFecSolicitud())));
				//fichero.write("; ");
				fichero.write(proces.cadena("fecEntrega", sdf.format(resultado.getRefConsultaPorDocumentoResponse().getEntrega().getDatosEntrega().getFecEntrega())));
				//fichero.write("; ");
				fichero.write(proces.cadena("tarjeta", resultado.getRefConsultaPorDocumentoResponse().getEntrega().getDatosOferta().getTarjeta()));
				//fichero.write("; ");
				fichero.write(proces.cadena("tipoProducto", resultado.getRefConsultaPorDocumentoResponse().getEntrega().getDatosOferta().getTipoProducto()));
				//fichero.write("; ");
				fichero.write(proces.cadena("descProducto", resultado.getRefConsultaPorDocumentoResponse().getEntrega().getDatosOferta().getDescProducto()));
				//fichero.write("; ");
				fichero.write(proces.cadena("primeros4Digitos", resultado.getRefConsultaPorDocumentoResponse().getEntrega().getDatosOferta().getPrimeros4Digitos()));
				//fichero.write("; ");
				fichero.write(proces.cadena("ultimos4Digitos", resultado.getRefConsultaPorDocumentoResponse().getEntrega().getDatosOferta().getUltimos4Digitos()));
				//fichero.write("; ");
				fichero.write(proces.cadena("contrato", resultado.getRefConsultaPorDocumentoResponse().getEntrega().getDatosOferta().getContrato()));
				//fichero.write("; ");
				fichero.write(proces.cadena("monto", decformat.format(resultado.getRefConsultaPorDocumentoResponse().getEntrega().getDatosOferta().getMonto())));
				//fichero.write("; ");
				fichero.write(proces.cadena("diaPago", resultado.getRefConsultaPorDocumentoResponse().getEntrega().getDatosOferta().getDiaPago()));
				//fichero.write("; ");
				fichero.write(proces.cadena("flagAcptoTratDat", resultado.getRefConsultaPorDocumentoResponse().getEntrega().getDatosOferta().getFlagAcptoTratDat().toString()));
				
				if(resultado.getRefConsultaPorDocumentoResponse().getEntrega().getDatosPersona().getTelefono()==null){
					fichero.write(proces.cadena("telefono", ""));
				}else{
					fichero.write(proces.cadena("telefono", resultado.getRefConsultaPorDocumentoResponse().getEntrega().getDatosPersona().getTelefono()));
				}
				
				if(resultado.getRefConsultaPorDocumentoResponse().getEntrega().getDatosPersona().getDireccion()==null){
					fichero.write(proces.cadena("dircliente", ""));
				}else{
					fichero.write(proces.cadena("dircliente", resultado.getRefConsultaPorDocumentoResponse().getEntrega().getDatosPersona().getDireccion()));
				}
				
				if(resultado.getRefConsultaPorDocumentoResponse().getEntrega().getDatosPersona().getExonerarVerifDomicilio()==null){
					fichero.write(proces.cadena("exoVD", ""));
				}else{
					String exonVD = null;
					Boolean val = resultado.getRefConsultaPorDocumentoResponse().getEntrega().getDatosPersona().getExonerarVerifDomicilio();
					if(val.booleanValue()==true){
						exonVD="1";
					}else{
						exonVD="0";
					}
					
					fichero.write(proces.cadena("exoVD", exonVD));
				}
				
				fichero.close();

			} catch (Exception ex) {
				System.out.println("Mensaje de la excepción: " + ex.getMessage());
			}
			
		}
		
	}
	
	public void saveErrorWS(String rutaofile){
		
		FileWriter fichero=null;
		
		try {
			fichero = new FileWriter(rutaofile);
			fichero.write("99");
			fichero.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void saveInputNotFound(String rutaofile){
		
		FileWriter fichero=null;
		
		try {
			fichero = new FileWriter(rutaofile);
			fichero.write("98");
			fichero.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void saveInputNotValid(String rutaofile){
		
		FileWriter fichero=null;
		
		try {
			fichero = new FileWriter(rutaofile);
			fichero.write("97");
			fichero.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void saveCabeceraNotValid(String rutaofile){
		
		FileWriter fichero=null;
		
		try {
			fichero = new FileWriter(rutaofile);
			fichero.write("96");
			fichero.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void saveErrorInesperado(String rutaofile){
		FileWriter fichero=null;
		
		try {
			fichero = new FileWriter(rutaofile);
			fichero.write("95");
			fichero.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void saveInvalidParameters(String rutaofile){
		FileWriter fichero=null;
		
		try {
			fichero = new FileWriter(rutaofile);
			fichero.write("94");
			fichero.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
