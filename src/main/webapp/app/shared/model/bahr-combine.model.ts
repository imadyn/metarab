export interface IBahrCombine {
  id?: number;
  code?: string;
  taille?: number;
  valeurRhythm?: string;
}

export class BahrCombine implements IBahrCombine {
  constructor(public id?: number, public code?: string, public taille?: number, public valeurRhythm?: string) {
  }
}

export interface IBahrBaitSearchCriteria {
  partie1?: string;
  partie2?: string;
}

export class BahrBaitSearchCriteria implements IBahrBaitSearchCriteria {
  constructor(public partie1?: string, public partie2?: string) {
  }
}
