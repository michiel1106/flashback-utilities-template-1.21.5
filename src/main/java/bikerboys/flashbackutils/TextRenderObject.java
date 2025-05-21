package bikerboys.flashbackutils;


import net.minecraft.text.Text;

public class TextRenderObject {

    public Text text;
    public int size;
    public int color;
    public int x;
    public int y;


    public TextRenderObject(net.minecraft.text.Text text, int size, int color, int x, int y) {
        this.text = text;
        this.size = size;
        this.color = color;
        this.x = x;
        this.y = y;
    }


    public Text getText() {
        return text;
    }

    public int getSize() {
        return size;
    }

    public int getColor() {
        return color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
