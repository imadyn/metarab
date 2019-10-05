export const enum Language {
  FRENCH = 'FRENCH',
  ENGLISH = 'ENGLISH',
  SPANISH = 'SPANISH',
  ARABIC = 'ARABIC'
}

export interface IRefAlphabet {
  id?: number;
  code?: string;
  name?: string;
  rhythm?: string;
  language?: Language;
}

export class RefAlphabet implements IRefAlphabet {
  constructor(public id?: number, public code?: string, public name?: string, public rhythm?: string, public language?: Language) {}
}
