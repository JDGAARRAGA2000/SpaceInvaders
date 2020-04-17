
package spaceinvaders;

public class Objeto {
    
                int posX, posY;            
                int velX, velY;
                int spriteInvasor;
                boolean vivo;
                boolean disparado = false;
            

    public Objeto(int cX, int cY, int vX, int vY, int spriteI)
    {
        posX = cX;
        posY = cY;
        velX = vX;
        velY = vY;
        spriteInvasor = spriteI;
    }
    
    public void setCoorX(int cX)
    {
        posX = cX;
    }
    
    public void setCoorY(int cY)
    {
        posY = cY;
    }
    
    public void setVelX(int vX)
    {
        velX = vX;
    }
    
    public void setVelY(int vY)
    {
        velY = vY;
    }
    
    public void setSprite(int sprite)
    {
        spriteInvasor = sprite;
    }
    
    
    public int getCoorX()
    {
        return posX;
    }
    
    public int getCoorY()
    {
        return posY;
    }
    
    
    public int getVelX()
    {
        return velX;
    }
    
    public int getVelY()
    {
        return velY;
    }
    
    public int getSprite()
    {
        return spriteInvasor;
    }
    
    
   
    
}
