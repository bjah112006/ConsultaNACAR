package com.bbva.test;

import java.io.IOException;
import java.util.List;

import com.grupobbva.pe.tat.ents.body.entrega.Entrega;
import com.grupobbva.pe.tat.service.message.ConsultaConFiltrosResponse;
import com.grupobbva.pe.tat.service.message.ConsultaPorDocumentoResponse;

public class Principal {

	public static void main(String[] args) throws IOException {
		
		String raiz = null;
		String ruta = null;
		String rutaifile = null;
		String rutaofile = null;
		String[] datosentrada = null;
		int tiporesultado;
		int cod_mensaje=0;
		
		
		Entrada ent = new Entrada();
		Salida sal = new Salida();
		
		raiz = ent.getRaiz();
		
		ruta = ent.getRuta(args, raiz);
		
		rutaifile = ent.getRutaInputFile(ruta);
		
		rutaofile=sal.getRutaOutputFile(ruta);
		
		sal.createOutputFile(rutaofile);
		
		datosentrada = ent.leerDatos(rutaifile);
		
		if (datosentrada==null){
			System.out.println("ERROR: No se encontró archivo de input");
			sal.saveInputNotFound(rutaofile);
		}else{
			//if (datosentrada[0].equals("") || datosentrada[1].equals("") || datosentrada[2].equals("")){
			if (datosentrada[0].equals("")){ 
				System.out.println("Cabecera no válida");
				sal.saveCabeceraNotValid(rutaofile);
			}else{
				
				if (datosentrada[7].toString().equals("Campos Incorrectos")){
					System.out.println("ERROR: Faltan Campos en el archivo de input");
					sal.saveInputNotValid(rutaofile);
				}else{
					
					if (datosentrada[0].toString().equals("1")){
						
						ConsultaPorDocumentoResponse entr = new ConsultaPorDocumentoResponse();
						
						Proceso proc = new Proceso();
			
						entr = proc.getResultDoc(datosentrada);
						
						tiporesultado = proc.gettiporesult();
						
						if(tiporesultado==1){
							System.out.println("ERROR: Incorrect WS Address..");
							sal.saveErrorWS(rutaofile);
						}else{
							
							cod_mensaje=Integer.parseInt(entr.getRefResponseHeader().getCodigoRespuesta().toString());
							
							switch (cod_mensaje){
							
							case 0:
								System.out.println("Se empezara a guardar");
								sal.saveDataDocumento(rutaofile, entr);
								System.out.println("Se guardó en la ruta: "+rutaofile);
								break;
							case 1:
								sal.saveErrorInesperado(rutaofile);
								System.out.println("Error");
								break;
							case 2:
								sal.saveCabeceraNotValid(rutaofile);
								System.out.println("Error");
								break;
							case 3:
								sal.saveCabeceraNotValid(rutaofile);
								System.out.println("Error");
								break;
							case 4:
								sal.saveCabeceraNotValid(rutaofile);
								System.out.println("Error");
								break;
							case 5:
								sal.saveInvalidParameters(rutaofile);
								System.out.println("Error");
								break;
							case 6:
								sal.saveInvalidParameters(rutaofile);
								System.out.println("Error");
								break;
							case 7:
								sal.saveInvalidParameters(rutaofile);
								System.out.println("Error");
								break;
							case 8:
								System.out.println("Sin datos");
								break;
							
							}
							
						}
															
						
					}
					
					//Si la búsqueda es Avanzada, datosentrada[0]="2"
					
					if (datosentrada[0].toString().equals("2")){
						
						ConsultaConFiltrosResponse entr;
						
						Proceso proc = new Proceso();
						entr = proc.getResultAvan(datosentrada);
						tiporesultado = proc.gettiporesult();
						
						
							if(tiporesultado==1){
								System.out.println("ERROR: Incorrect WS Address..");
								sal.saveErrorWS(rutaofile);
							}else{
								cod_mensaje=Integer.parseInt(entr.getRefResponseHeader().getCodigoRespuesta().toString());
								
								switch (cod_mensaje){
								
								case 0:
									if(entr.getRefConsultaConFiltrosResponse().getTotal().intValue()==0){
										System.out.println("Sin datos");
									}else{
										System.out.println("Se empezara a guardar");
										if(datosentrada[6].equals("")){
											entr = proc.ordenarLista(entr);
											entr.getRefConsultaConFiltrosResponse().setListadoEntregas(proc.filtrarLista(entr));
										}
										sal.saveDataAvanzada(rutaofile, entr);
										System.out.println("Se guardó en la ruta: "+rutaofile);
									}
									break;
									
								case 1:
									sal.saveErrorInesperado(rutaofile);
									System.out.println("Error inesperado");
									break;
								case 2:
									sal.saveCabeceraNotValid(rutaofile);
									System.out.println("Error cabecera no válida");
									break;
								case 3:
									sal.saveCabeceraNotValid(rutaofile);
									System.out.println("Error cabecera no válida");
									break;
								case 4:
									sal.saveCabeceraNotValid(rutaofile);
									System.out.println("Error cabecera no válida");
									break;
								case 8:
									System.out.println("Sin datos");
									break;
								
								}
							}		
						
					}
					
					if (!datosentrada[0].toString().equals("1") && !datosentrada[0].toString().equals("2")){
						sal.saveCabeceraNotValid(rutaofile);
						System.out.println("Error cabecera no válida. Tipo de búsqueda diferente de '1' o '2'");
					}
					
				}
				
			}
						
		}
		
		//Si la búsqueda es por Documento, datosentrada[0]="1"
		
		/////////Salida de datos///////////
		

	}

}
