package controller;

import model.Animal;

/**
 * Interface de callback pour la modification d'un animal
 */
public interface AnimalModificationCallback {

    /**
     * Méthode appelée lorsqu'un animal est modifié
     *
     * @param oldAnimal L'animal avant modification
     * @param newAnimal L'animal après modification
     */
    void onAnimalModified(Animal oldAnimal, Animal newAnimal);

    /**
     * Méthode appelée lorsqu'un animal est ajouté
     *
     * @param animal L'animal ajouté
     */
    void onAnimalAdded(Animal animal);

    /**
     * Méthode appelée lorsqu'un animal est supprimé
     *
     * @param animal L'animal supprimé
     */
    void onAnimalDeleted(Animal animal);
}