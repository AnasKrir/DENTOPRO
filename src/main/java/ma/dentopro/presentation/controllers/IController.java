package ma.dentopro.presentation.controllers;

import ma.dentopro.presentation.vue.themes.Theme;


public interface IController <T,ID >{
    void showAll();
    void showNewElementForm();
    void showUpdateForm();
    void save(T newElement);
    void update(T newValuesElement);
    void delete(T element);

}
