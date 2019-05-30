package co.edu.javeriana.algoritmos.proyecto.reakt;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import co.edu.javeriana.algoritmos.proyecto.ColorJugador;
import co.edu.javeriana.algoritmos.proyecto.Jugada;
import co.edu.javeriana.algoritmos.proyecto.Tablero;
import java.util.ArrayList;
import java.util.List;
import java.awt.Point;
import java.util.Iterator;
import java.util.Random;
import java.util.stream.Collectors;

/**
 *
 * @author USUARIO
 */
public class TableroHex implements Tablero {
    ColorJugador tablero[][];
    public boolean log;
    public Jugador jugador;
    public int Pot[][][];
    public int Bridge[][][];
    public boolean Upd[][];
    
    List<Point> Celdas;
    List<Point> Libres;
    List<Point> Blancas;
    List<Point> Negras;
    
    public int Size = 11;
    int turno;
    
    public TableroHex(Jugador j,boolean l) {
        this();
        jugador = j;
        log = l;
    }
    public TableroHex() {
        tablero = new ColorJugador[Size][Size];
        Pot = new int[Size][Size][4];
        Bridge = new int[Size][Size][4];
        Upd = new boolean[Size][Size];
        
        Celdas = new ArrayList<>();
        Libres = new ArrayList<>();
        Blancas = new ArrayList<>();
        Negras = new ArrayList<>();
        
        turno = 1;

        for (int i = 0; i < Size; i++) {
            for (int j = 0; j < Size; j++) {
                Libres.add(new Point(i,j));
                Celdas.add(new Point(i,j));
            }
        }
        
    }
    @Override
    public ColorJugador ganador() {
        return jugador!=null?jugador.colorJugador:ColorJugador.NEGRO;
    }

    @Override
    public ColorJugador casilla(int fila, int columna) {
        return tablero[fila][columna];
    }
    
    @Override
    public void aplicarJugada(Jugada jugada, ColorJugador colorJugador) {
        if(!jugada.isCambioColores())
        {
            int x = jugada.getFila();
            int y = jugada.getColumna();
            aplicarJugada(new Point(x, y), colorJugador);
            Libres.remove(new Point(x,y));
        }
        
        turno++;
    }

    private void aplicarJugada(Point p, ColorJugador colorJugador) {
        tablero[p.x][p.y]=colorJugador;
        ((colorJugador==ColorJugador.BLANCO)?Blancas:Negras).add(p);
        ((colorJugador!=ColorJugador.BLANCO)?Blancas:Negras).remove(p);
        if(log)System.out.println(" > Aplicar Jugada ["+p.getX()+","+p.getY()+"]");
    }
    
    private void aplicarJugada(Point p, ColorJugador colorJugador, Iterator<Point> iterator) {
        aplicarJugada(p, colorJugador);
        iterator.remove();
    }    
    
    public List<Point> getLibres() {
        return Libres;
    }

    public List<Point> getBlancas() {
        return Blancas;
    }

    public List<Point> getNegras() {
        return Negras;
    }
        
    public Jugada encontrarMejorJugada(Tablero t) {
        //Actualizar lista de casillas libres
        Actualizar(t);
        //Escoger la unica casilla libre por si acaso
        jugador.mejorJugada = newJugada(Libres.get(0));
        jugador.mejorJugada
            = (turno==1)? FirstMove()
            : (turno==2)? SecondMove(jugador.colorJugador)
            : GetBestMove(jugador.colorJugador);
        return jugador.mejorJugada;
    }  
    
    public void Actualizar(Tablero t){
        for (Iterator<Point> iterator = Libres.iterator(); iterator.hasNext(); ) {
            Point p = iterator.next();
            if(t.casilla(p.x,p.y)!=null)
            {
                if(log)System.out.println(" >Enemigo jugo en [x="+ p.x+ ",y="+p.y+"] turno: " +(turno));
                aplicarJugada(p, t.casilla(p.x,p.y),iterator);
                turno++;
                break;
            }
        }
    }
    
    public ColorJugador casilla(Point p) {
        return tablero[p.x][p.y];
    }
    
    public Jugada newJugada(Point p){
        return new Jugada(p.x, p.y);
    }
    private int random(int n){
        Random r = new Random();
        return r.nextInt(n);
    }
    private boolean isOpuesto (ColorJugador c1, ColorJugador c2){
        return c1!=c2 &&  c1!=null && c2!=null;
    }
    private ColorJugador Opuesto (ColorJugador c){
        if(c!=null)
            return c==ColorJugador.BLANCO?ColorJugador.NEGRO:ColorJugador.BLANCO;
        return null;
    }
    private int sign(int n){
        if (n<0) return(-1);
        if (n>0) return(1);
        return 0;
    }
    private boolean OutOfBounds(int x, int y){
	return x<0 || y<0 || x>=Size || y>=Size;
    }
    public void SetUpd(int x,int y){
        if (OutOfBounds(x,y))
            return;
        Upd[x][y]=true;
    }
    public ColorJugador GetCell(int x,int y){
        if (x<0)     return ColorJugador.NEGRO;
        if (y<0)     return ColorJugador.BLANCO;
        if (x>=Size) return ColorJugador.NEGRO;
        if (y>=Size) return ColorJugador.BLANCO;
        return tablero[x][y];
    }
    
    public Jugada FirstMove(){
        //Escoger una de las esquinas
        int x = random(4);
        int y = random(4-x);
        
        // 50-50 de escojer la otra
        if (random(2)<1)
        {
            x=Size-1-x;
            y=Size-1-y;
        }
        return new Jugada(x,y);
    }
    
    public Jugada SecondMove(ColorJugador colorJugador){
        //Buscar en la lista de movimientos otro jugador, solo deberia haber uno
        for(Point p : (colorJugador==ColorJugador.BLANCO? Negras: Blancas)){
            if ((p.x+p.y<=2)||(p.x+p.y>=2*Size-4))
                return GetBestMove(colorJugador);
            else
                return new Jugada(true, p.x, p.y);
	}
        return new Jugada(Libres.get(0).x,Libres.get(0).y);
    }  
    
    
    public void CalculatePotential(ColorJugador cJugador){
	int borderPotential=128;

	for (int x=0; x<Size; x++)
            for (int y=0; y<Size; y++)
                for (int p=0; p<4; p++)
                {
                    Pot[x][y][p]=20000;
                    Bridge[x][y][p]=0;
                }

	for (int i=0; i<Size; i++)
	{
            if (casilla(i,0)==null)
                Pot[i][0][0]=borderPotential;
            if (casilla(i,0)==ColorJugador.BLANCO)
                Pot[i][0][0]=0;

            if(casilla(i,Size-1)==null)
                Pot[i][Size-1][1]=borderPotential;
            if (casilla(i,Size-1)==ColorJugador.BLANCO)
                Pot[i][Size-1][1]=0;

            if (casilla(0,i)==null)
                Pot[0][i][2]=borderPotential;
            if (casilla(0,i)==ColorJugador.NEGRO)
                Pot[0][i][2]=0;

            if(casilla(Size-1,i)==null)
                Pot[Size-1][i][3]=borderPotential;
            if (casilla(Size-1,i)==ColorJugador.NEGRO)
                Pot[Size-1][i][3]=0;
	}

	CalculatePlayerPotential(0,ColorJugador.BLANCO,cJugador);
	CalculatePlayerPotential(1,ColorJugador.BLANCO,cJugador);
	CalculatePlayerPotential(2,ColorJugador.NEGRO,cJugador);
	CalculatePlayerPotential(3,ColorJugador.NEGRO,cJugador);
    }

    public void CalculatePlayerPotential(int k,ColorJugador color, ColorJugador cJugador){
        int max,total;
        for (int x=0; x<Size; x++)
            for (int y=0; y<Size; y++)
                Upd[x][y]=true;

        max=0; 
        do
        {
            max++;
            total=0;
            for (int x=0; x<Size; x++)
                for (int y=0; y<Size; y++)
                    if (Upd[x][y])
                        total+=SetPot(x, y, k, color, cJugador);
            
            for (int x=Size-1; x>=0; x--)
                for (int y=Size-1; y>=0; y--)
                    if (Upd[x][y])
                        total+=SetPot(x, y, k, color, cJugador);
        }
        while(total>0 && max<12);
    }

    public int SetPot(int x,int y,int p,ColorJugador color, ColorJugador cJugador)
    {
	int  mm, ddb=0, nn, oo=0, dd=140, bb=66;
       
	Upd[x][y]=false;
        Bridge[x][y][p]=0;

        //Enemy cells have 0 potential
        if (isOpuesto(tablero[x][y],color))
            return 0;
        if (color!=cJugador)
            bb=52;
  
        int vecinos[] ={
            GetPot(x+1 ,y   ,p ,color),
            GetPot(x   ,y+1 ,p ,color),
            GetPot(x-1 ,y+1 ,p ,color),
            GetPot(x-1 ,y   ,p ,color),
            GetPot(x   ,y-1 ,p ,color),
            GetPot(x+1 ,y-1 ,p ,color)};
        
        int tt[]=new int[6];
        for (int i=0; i<6; i++)
        {
            if ((vecinos[i]>=30000)&&(vecinos[(i+2)%6]>=30000))
            {
                if (vecinos[(i+1)%6]<0)
                    ddb=+32;
                else
                  vecinos[(i+1)%6]+=128; //512;
            }
        }
        for (int i=0; i<6; i++)
        {
            if ((vecinos[i]>=30000)&&(vecinos[(i+3)%6]>=30000))
            {
                ddb+=30;
            }
        }
        
        mm=30000;
        for (int i=0; i<6; i++)
        {
            if (vecinos[i]<0)
            {
                vecinos[i]+=30000;
                tt[i]=10;
            }
            else
                tt[i]=1;
            if (mm>vecinos[i])
                mm=vecinos[i];     
        }
        nn=0;
        for (int i=0; i<6; i++)
        {
            if (vecinos[i]==mm)
                nn+=tt[i];
        }
        Bridge[x][y][p]=nn/5;
        if ((nn>=2)&&(nn<10)) {
            Bridge[x][y][p]=bb+nn-2; mm-=32;
        }
        if (nn<2)
        {
            oo=30000;
            for (int i=0; i<6; i++)
            {
                if ((vecinos[i]>mm)&&(oo>vecinos[i]))
                    oo=vecinos[i];     
            }
            if (oo<=mm+104) {
                Bridge[x][y][p]=bb-(oo-mm)/4; mm-=64;
            }
            mm+=oo;
            mm/=2;
        }

        if ((x>0)&&(x<Size-1)&&(y>0)&&(y<Size-1))
            Bridge[x][y][p]+=ddb;
        else 
            Bridge[x][y][p]-=2;
        if (((x==0)||(x==Size-1))&&((y==0)||(y==Size-1)))
            Bridge[x][y][p]/=2;
        if (Bridge[x][y][p]>68)
            Bridge[x][y][p]=68;

        if (tablero[x][y]==color)
        {
            if (mm<Pot[x][y][p]) 
            {
                Pot[x][y][p]=mm;
                SetUpd(x+1 ,y  );
                SetUpd(x   ,y+1);
                SetUpd(x-1 ,y+1);
                SetUpd(x-1 ,y  );
                SetUpd(x   ,y-1);
                SetUpd(x+1 ,y-1);
                return(1);
            }  
            return(0);
        }
        if (mm+dd<Pot[x][y][p]) 
        {
            Pot[x][y][p]=mm+dd;
            SetUpd(x+1 ,y  );
            SetUpd(x   ,y+1);
            SetUpd(x-1 ,y+1);
            SetUpd(x-1 ,y  );
            SetUpd(x   ,y-1);
            SetUpd(x+1 ,y-1);
            return(1);
        }
        return(0);
    }

    public int GetPot(int x,int y,int p, ColorJugador color)
    {
        if (OutOfBounds(x,y))
            return 30000;

        if (tablero[x][y]==null)
            return Pot[x][y][p];
        if (isOpuesto(tablero[x][y],color))
            return 30000;
        return Pot[x][y][p]-30000;
    }
    
    public Jugada GetBestMove(ColorJugador colorJugador)
    {
        CalculatePotential(colorJugador);
        
        int i_b=Libres.get(0).x, j_b=Libres.get(0).y, ff=0, i_q=0, j_q=0, cc, pp0, pp1,mm,mmp;
        int vv[]=new int[Size*Size];
        if (turno>1)
            ff=190/((turno-1)*(turno-1));
        mm=20000;

        for (int x=0; x<Size; x++)
        { 
            for (int y=0; y<Size; y++)
            { 
                if (tablero[x][y]!=null)
                { i_q+=2*x+1-Size;
                    j_q+=2*y+1-Size;
                }
            }
        }
        i_q=sign(i_q);
        j_q=sign(j_q);

        for (int i=0; i<Size; i++)
        { 
            for (int j=0; j<Size; j++)
            { 
                if (tablero[i][j]==null)
                {
                    mmp =(Math.abs(i-5)+Math.abs(j-5))*ff;
                    mmp+=8*(i_q*(i-5)+j_q*(j-5))/(turno);

                    for (int k=0; k<4; k++)
                        mmp-=Bridge[i][j][k];

                    pp0=Pot[i][j][0]+Pot[i][j][1];
                    pp1=Pot[i][j][2]+Pot[i][j][3];
                    mmp+=pp0+pp1;

                    if ((pp0<=268)||(pp1<=268)) mmp-=400; //140+128
                    vv[i*Size+j]=mmp;          

                    if (mmp<mm)
                    { mm=mmp; 
                        i_b=i;
                        j_b=j;
                    }  
                }  
            }
        }

        mm+=108;
        for (int i=0; i<Size; i++)
        { 
            for (int j=0; j<Size; j++)
            { 
                if (vv[i*Size+j]<mm)
                { 
                    if (colorJugador == ColorJugador.NEGRO)
                    { 
                        if ((i>3)&&(i<Size-1)&&(j>0)&&(j<3)) 
                        { 
                            if (isOpuesto(colorJugador,tablero[i-1][j+2]))
                            {
                                cc=CanConnectFarBorder(i-1,j+2,Opuesto(colorJugador));
                                if (cc<2) 
                                {
                                    i_b=i; 
                                    if (cc<-1) {
                                        i_b--; 
                                        cc++;
                                    }
                                    j_b=j-cc; 
                                    mm=vv[i*Size+j]; 
                                }
                            }
                        }

                        if ((i>0)&&(i<Size-1)&&(j==0))
                        { 
                            if (isOpuesto(colorJugador,tablero[i-1][j+2])
                                    && (tablero[i-1][j]==null)
                                    && (tablero[i-1][j+1]==null)
                                    && (tablero[i][j+1]==null)
                                    && (tablero[i+1][j]==null))
                                {
                                    i_b=i;
                                    j_b=j;
                                    mm=vv[i*Size+j];
                                }  
                        }

                        if ((i>0)&&(i<Size-4)&&(j<Size-1)&&(j>Size-4)) 
                        { 
                            if (isOpuesto(colorJugador,tablero[i+1][j-2]))
                            {
                                cc=CanConnectFarBorder(i+1,j-2,Opuesto(colorJugador));
                                if (cc<2) 
                                {
                                    i_b=i;
                                    if (cc<-1) {
                                        i_b++;
                                        cc++;
                                    }
                                    j_b=j+cc; 
                                    mm=vv[i*Size+j]; 
                                }
                            }  
                        }

                        if ((i>0)&&(i<Size-1)&&(j==Size-1))
                        { 
                            if (isOpuesto(colorJugador,tablero[i+1][j-2])
                                &&(tablero[i-1][j]==null)
                                &&(tablero[i+1][j-1]==null)
                                &&(tablero[i][j-1]==null)
                                &&(tablero[i+1][j]==null))
                                {
                                    i_b=i;
                                    j_b=j;
                                    mm=vv[i*Size+j];
                                }  
                        }
                    }
                    else
                    { 
                        if ((j>3)&&(j<Size-1)&&(i>0)&&(i<3)) 
                        { 
                            if (isOpuesto(colorJugador,tablero[i+2][j-1]))
                            {
                                cc=CanConnectFarBorder(i+2,j-1,Opuesto(colorJugador)); 
                                if (cc<2) 
                                {
                                    j_b=j; 
                                    if (cc<-1) {
                                        j_b--;
                                        cc++;
                                    }
                                    i_b=i-cc; 
                                    mm=vv[i*Size+j]; 
                                }
                            }  
                        }

                        if ((j>0)&&(j<Size-1)&&(i==0))
                        { 
                            if (isOpuesto(colorJugador,tablero[i+2][j-1])
                                &&(tablero[i][j-1]==null)
                                &&(tablero[i+1][j-1]==null)
                                &&(tablero[i+1][j]==null)
                                &&(tablero[i][j+1]==null))
                            {
                                i_b=i;
                                j_b=j;
                                mm=vv[i*Size+j];
                            }  
                        }

                        if ((j>0)&&(j<Size-4)&&(i<Size-1)&&(i>Size-4)) 
                        { 
                            if (isOpuesto(colorJugador,tablero[i-2][j+1]))
                            {
                                cc=CanConnectFarBorder(i-2,j+1,Opuesto(colorJugador));
                                if (cc<2) 
                                {
                                    j_b=j; 
                                    if (cc<-1) {
                                        j_b++;
                                        cc++;
                                    }
                                    i_b=i+cc; 
                                    mm=vv[i*Size+j]; 
                                }
                            }  
                        }

                        if ((j>0)&&(j<Size-1)&&(i==Size-1))
                        { 
                            if (isOpuesto(colorJugador,tablero[i-2][j+1])
                                &&(tablero[i][j+1]==null)
                                &&(tablero[i-1][j+1]==null)
                                &&(tablero[i-1][j]==null)
                                &&(tablero[i][j-1]==null))
                            {
                                i_b=i;
                                j_b=j;
                                mm=vv[i*Size+j];
                            }
                        }          
                    }
                }
            }
        }
        return new Jugada(i_b,j_b);
    }
    
    public int CanConnectFarBorder(int nn, int mm, ColorJugador color)
    {
        if (color==ColorJugador.BLANCO) //blue
        { 
            if (2*mm<Size-1)
            { 
                for (int x=0; x<Size; x++)
                { 
                    for (int y=0; y<mm; y++)
                    { 
                        if ((y-x<mm-nn)&&(x+y<=nn+mm)&&(tablero[x][y]!=null))
                            return 2;
                    }
                }

                if (isOpuesto(color,tablero[nn-1][mm]))
                    return 0;

                if (isOpuesto(color,tablero[nn-1][mm-1]))
                { 
                    if (isOpuesto(color,GetCell(nn+2,mm-1)))
                        return 0;
                    else
                        return -1;
                }

                if (isOpuesto(color,GetCell(nn+2,mm-1)))
                    return -2;
            }
            else
            { 
                for (int x=0; x<Size; x++)
                { 
                    for (int y=Size-1; y>mm; y--)
                    { 
                        if ((y-x>mm-nn)&&(x+y>=nn+mm)&&(tablero[x][y]!=null))
                            return 2;
                    }
                }

                if (isOpuesto(color,tablero[nn+1][mm]))
                    return 0;

                if (isOpuesto(color,tablero[nn+1][mm+1]))
                { 
                    if (isOpuesto(color,GetCell(nn-2,mm+1)))
                        return 0;
                    else
                        return -1;
                }

                if (isOpuesto(color,GetCell(nn-2,mm+1)))
                    return -2;
            }  
        }
        if(color==ColorJugador.NEGRO)
        { 
            if (2*nn<Size-1)
            { 
                for (int y=0; y<Size; y++)
                { 
                    for (int x=0; x<nn; x++)
                    { 
                        if ((x-y<nn-mm)&&(x+y<=nn+mm)&&(tablero[x][y]!=null)) 
                            return 2;
                    }
                }

                if (isOpuesto(color,tablero[nn][mm-1]))
                    return 0;

                if (isOpuesto(color,tablero[nn-1][mm-1]))
                { 
                    if (isOpuesto(color,GetCell(nn-1,mm+2)))
                        return 0;
                    else
                        return -1;
                }

                if (isOpuesto(color,GetCell(nn-1,mm+2)))
                    return -2;
            }
            else
            { 
                for (int y=0; y<Size; y++)
                { 
                    for (int x=Size-1; x>nn; x--)
                    { 
                        if ((x-y>nn-mm)&&(x+y>=nn+mm)&&(tablero[x][y]!=null)) 
                            return 2;
                    }
                }

                if (isOpuesto(color,tablero[nn][mm+1]))
                    return 0;

                if (isOpuesto(color,tablero[nn+1][mm+1]))
                { 
                    if (isOpuesto(color,GetCell(nn+1,mm-2)))
                        return 0;
                    else
                        return -1;
                }

                if (isOpuesto(color,GetCell(nn+1,mm-2)))
                    return -2;
            }  
        }  
        return 1;
    }

    /**
     * imprimesion del tablero para ver movimientos
     * Negro es O
     * Blanco es X
     * Nada es -
     */

    public void imprimirTablero() {
        int n = 11;
        
        System.out.println("  | ____________________: "+(turno-1));
        System.out.println("  | A B C D E F G H I J K    ");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                System.out.print(" ");
            }
            for (int j = 0; j < n; j++) {
                if (j==0){
                    String v=(i>=9) ? "" : " ";
                    System.out.print((i+1)+ v+"|");
                }
                if (tablero[i][j] == null) {
                    System.out.print(" -");
                }
                if (tablero[i][j] == ColorJugador.NEGRO) {
                    System.out.print(" O");
                }
                if (tablero[i][j] == ColorJugador.BLANCO) {
                    System.out.print(" X");
                }
            }
            System.out.print("\n");
        }
        System.out.println("========================================");
        String resultL = Libres.stream().map(p->"("+p.x+","+p.y+")").collect(Collectors.joining(","));
        System.out.println("Libres: "+resultL);
        String resultB = Blancas.stream().map(p->"("+p.x+","+p.y+")").collect(Collectors.joining(","));
        System.out.println("Blancas: "+resultB);
        String resultN = Negras.stream().map(p->"("+p.x+","+p.y+")").collect(Collectors.joining(","));
        System.out.println("Negras: "+resultN);
    }
}
