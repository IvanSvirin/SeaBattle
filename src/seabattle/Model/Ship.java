package seabattle.Model;

/**
 * Created by ivan on 20.04.2015.
 */
public class Ship {
    int deckQty;    //количество палуб
    public int liveDeckQty;
    Cell upperLeftCell;
    boolean isHorizontal;    //ориентация: true - горизонтальная, false - вертикальная
    Field field;

    public Ship(Cell upperLeftCell, int deckQty, boolean isHorizontal, Field field) {
        this.upperLeftCell = upperLeftCell;
        this.deckQty = deckQty;
        liveDeckQty = deckQty;
        this.isHorizontal = isHorizontal;
        this.field = field;
        //разьещение на поле всех палуб корабля
        for (int i = 0; i < deckQty; i++) {
            if (isHorizontal) {
                field.cells[upperLeftCell.x + i][upperLeftCell.y].ship = this;
            }
            else {
                field.cells[upperLeftCell.x][upperLeftCell.y + i].ship = this;
            }
        }
    }
}
