/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.ArrayList;
import model.History;

/**
 *
 * @author gf63
 */
public interface ServiceHistory {
    ArrayList < History> getByUser(int uid);
}
