package com.avalon.coe.PrisonersDelimmaGA.GeneticAlgorithm;

import java.util.Random;

/**
 *
 */
public class Breeding {

  private final int population;
  private final int chromosomeSize;
  private final double mutateProb;
  private final double crossOverProb;
  private Random rand = new Random();
  private int lastparent;	//index of last prisoner selected
  private int[] scores;

  public Breeding(int pop, int chromosomeSize, double mutateProb, double crossOverProb) {
    this.population = pop;
    this.chromosomeSize = chromosomeSize;
    this.mutateProb = mutateProb;
    this.crossOverProb = crossOverProb;
    this.scores = new int[this.population];
  }

  public Chromosome[] breedChromosomes(Chromosome[] chromosomesOrig) {
    Chromosome[] newChromosomes = new Chromosome[this.population];
    if (chromosomesOrig[0] == null) {
      for (int i = 0; i < this.population; i++) {
        newChromosomes[i] = new Chromosome(this.chromosomeSize);
      }
      return newChromosomes;
    } else {
      //TODO breed
      //TODO select 2 parents for 2 offspring

      int newPopsize = 0;
      while (newPopsize < this.population) {
        Chromosome c1 = chromosomesOrig[selectRoulette()];
        Chromosome c2 = chromosomesOrig[selectRoulette()];

        //Cross Over
        if(rand.nextDouble() <= this.crossOverProb) {
          Chromosome[] chromosomes = c1.crossover(c2);
          chromosomes[0].mutate(this.mutateProb);
          chromosomes[1].mutate(this.mutateProb);
          newChromosomes[newPopsize] = chromosomes[0];
          newChromosomes[newPopsize + 1] = chromosomes[1];
        } else {

        }

        newPopsize += 2;
      }
    }
    return chromosomesOrig;
  }

  /**
   * Roulette wheel selection
   * Fitness Proportionate Selection
   */
  private int selectRoulette() {
    double t1, fitSum = 0;
    int target;

    //Set Target Fitness
    for(int j = 0; j < this.population; j++)
      fitSum += scores[j];
    t1 = fitSum * rand.nextDouble();
    target = (int)t1;

    //Build up a sum of fitness
    //the individual who's fitness causes the sum to
    //exceed the target is selected
    int fitness = 0;
    int nextparent = lastparent;
    while(fitness < target)
    {
      nextparent++;
      if(nextparent >= this.population)
        nextparent = 0;

      if(nextparent != this.lastparent)
        fitness += this.scores[nextparent];
    }
    lastparent = nextparent;
    return nextparent; //return index of selected player
  }

  private void getScores(Chromosome[] chromosomes) {
    for (int i = 0; i < this.population; i++) {
      this.scores[i] = chromosomes[i].getGameScore();
    }
  }

}