/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java;

import co.edu.javeriana.algoritmos.proyecto.ColorJugador;
import co.edu.javeriana.algoritmos.proyecto.Jugada;
import co.edu.javeriana.algoritmos.proyecto.JugadorHex;
import co.edu.javeriana.algoritmos.proyecto.Tablero;
import java.awt.Point;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author Reakt
 */
public class Jugador implements JugadorHex{
	public final long MAX_CALCULATION_TIME = 1000*2-1;
	public final TimeUnit MAX_CALCULATION_UNITS = TimeUnit.MILLISECONDS;

	private Jugada mejorJugada;
	private final TableroHex tableroInterno;

	public Jugador(){
		mejorJugada = null;
		tableroInterno = new TableroHex();
	}

	public Jugada newJugada(Point p){
		return new Jugada(p.x, p.y);
	}

	public Jugada encontrarMejorJugada(Tablero tablero, ColorJugador color) throws InterruptedException {
		//Actualizar lista de casillas libres
		tableroInterno.Actualizar(tablero);

		//Escoger la unica casilla libre por si acaso
		mejorJugada = newJugada(tableroInterno.Libres.get(0));

		//TODO: Algoritmo que escoge la mejor casilla para hacer movimiento
		Thread.sleep(3000);
		mejorJugada = newJugada(tableroInterno.Libres.get(1));

		tableroInterno.aplicarJugada(mejorJugada, color); 
		return mejorJugada;
	}


	public Jugada MovimientoGanador(Tablero tablero,ColorJugador miColor, int MoveCount) {
		Jugada jugada;
		int colorBlanco=-1,colorNegro=1,color;
		String colorin=miColor.name();
		if(colorin=="BLANCO") {
			color=-1;
		}else {
			color=1;
		}
		int conexion,auxiliar=0, auxiliar2=0,aux3=0,aux4=0;
		double mmp,mm=20000;
		 double [] vv;
		 vv= new double [100];
		for (int i=0; i<11; i++)
		{ for (int j=0; j<11; j++)
		    { if (tablero.casilla(i, j)!=null)
		      { aux3+=2*i+1-11;
		        aux4+=2*j+1-11;
		      }
		    }
		  }
		 aux3=aux3/aux3;
		  aux4=aux4/aux4;
		  for (int i=0; i<11; i++)
		  { for (int j=0; j<11; j++)
		    { if (tablero.casilla(i, j)==null)
		      { 
		    	mmp = Math.random()*(49-3*16);
		        mmp+=(Math.abs(i-5)+Math.abs(j-5))*190;
		        mmp+=8*(aux3*(i-5)+aux4*(j-5))/(MoveCount+1);
		         for (int k=0; k<4; k++)
		          //  mmp-=Bridge[i][j][k];
		        
		      //  pp0=Pot[i][j][0]+Pot[i][j][1];
		        //pp1=Pot[i][j][2]+Pot[i][j][3];
		        //mmp+=pp0+pp1;
		      //  if ((pp0<=268)||(pp1<=268)) mmp-=400; //140+128
		       vv[i*11+j]=mmp;          
		        if (mmp<mm)
		        { mm=mmp; 
		          auxiliar=i;
		          auxiliar2=j;
		        }  
		      }  
		    }
		  }
			
		for(int i=0;i<11;i++) {
			for(int j=0;j<11;j++) {
				if(color<0) {//blanco
					if(i>3&&i<(11-1)&&j>0&&j<3) {

						if(tablero.casilla(i-1, j+2)!=null&&tablero.casilla(i-1, j+2)!=miColor) {
							//conexion=llamar funcuion validacion;
							if(conexion<2) {
								auxiliar=i;
								if(conexion<-1) {
									auxiliar--;
									conexion++;
								}
								j=j-conexion;
								mm=vv[i*11+j];   
							}
						} 
					}
					if (i>0&&i<(11-1)&&j==0)
					{ if ((tablero.casilla(i-1, j+2)!=miColor)&&(tablero.casilla(i-1, j)==null)&&(tablero.casilla(i-1, j+1)==null)&&(tablero.casilla(i, j+1)==null)&&(tablero.casilla(i+1, j)==null))
					{ auxiliar=i; 
					auxiliar2=j;
					 mm=vv[i*11+j]; 
					}  
					}
					if ((i>0)&&(i<11-4)&&(j<11-1)&&(j>11-4)) 
					{ if (tablero.casilla(i+1, j-2)!=null&&tablero.casilla(i+1, j-2)!=miColor)
					{ conexion=sepuedeconectarfuncion;
					if (conexion <2)
					{ auxiliar=i; 
					if (conexion<-1) {
						auxiliar2++;
						conexion++; 
					}
					auxiliar2=j+conexion; 
					 mm=vv[i*11+j]; 
					}
					}  
					}
					if ((i>0)&&(i<11-1)&&(j==11-1))

					{ 
						if ((tablero.casilla(i+1, j-2)!=miColor)&&(tablero.casilla(i+1, j)==null)&&(tablero.casilla(i+1, j-1)==null)&&(tablero.casilla(i, j-1)==null)&&(tablero.casilla(i-1, j)==null))


						{ auxiliar=i; 
						auxiliar2=j; 
						mm=vv[i*11+j];
						}  
					}
				}
				
				else {

					if ((j>3)&&(j<11-1)&&(i>0)&&(i<3)) 

					{ if (tablero.casilla(i+2, j-1)!=null&&tablero.casilla(i+2, j-1)!=miColor)
					{ conexion=funcion se puede;
					if (conexion<2) 
					{ auxiliar2=j; 
					if (conexion<-1) { 
						auxiliar2--; 
						conexion++; }
					auxiliar=i-conexion; 
					mm=vv[i*11+j]; 
					}
					}  
					}
					if ((j>0)&&(j<11-1)&&(i==0))
						if ((tablero.casilla(i+2, j-1)!=miColor)&&(tablero.casilla(i, j-1)==null)&&(tablero.casilla(i+1, j-1)==null)&&(tablero.casilla(i+1, j)==null)&&(tablero.casilla(i, j+1)==null))
						{
							{ auxiliar=i;
							auxiliar2=j; 
							mm=vv[i*11+j]; 
							}  
						}
					if ((j>0)&&(j<11-4)&&(i<11-1)&&(i>11-4)) 

					{ if (tablero.casilla(i-2, j+1)!=null&&tablero.casilla(i-2, j+1)!=miColor)
					{ conexion=funcionsi es pisble;
					if (conexion<2) 
					{ auxiliar2=j; 
					if (conexion<-1) { 
						auxiliar2++;
						conexion++; 
					}
					auxiliar=i+conexion; 
					// mm=vv[ii*Size+jj]; 
					}
					}  
					}
					if ((j>0)&&(j<11-1)&&(i==11-1))


					{ if ((tablero.casilla(i-2, j+1)!=miColor)&&(tablero.casilla(i, j+1)==null)&&(tablero.casilla(i-1, j+1)==null)&&(tablero.casilla(i-1, j)==null)&&(tablero.casilla(i, j-1)==null)){ auxiliar=i; 
					auxiliar2=j;
					mm=vv[i*11+j]; 
					}  
					} 



				}





			}
		}
		return jugada;
	}










	@Override
	public Jugada jugar(Tablero tablero, ColorJugador color) {
		final ExecutorService service = Executors.newSingleThreadExecutor();
		try {
			final Future<Jugada> f = service.submit(() -> {
				encontrarMejorJugada(tablero,color);
				return mejorJugada;
			});
			mejorJugada = f.get(MAX_CALCULATION_TIME, MAX_CALCULATION_UNITS);
		} catch (final InterruptedException | ExecutionException | TimeoutException e) {
		} finally {
			service.shutdown();
			return mejorJugada;
		}
	}





	@Override
	public String nombreJugador() {
		return "Reakt";
	}


}







