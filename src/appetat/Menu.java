/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appetat;

/**
 *
 * @author Castor
 */
public class Menu {

    protected String[] options;

    public Menu(String... options) {
        this.options = options;
    }

    public String[] getOptions() {
        return options;
    }
}
