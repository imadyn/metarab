export const enum Type {
  HACHW = 'HACHW',
  AROUD = 'AROUD',
  DARB = 'DARB',
  RYTHME = 'RYTHME',
  MESURE = 'MESURE'
}

export interface ITypeTB {
  id?: number;
  code?: string;
  ordre?: string;
  type?: Type;
  refBahrId?: number;
  refRhythmId?: number;
}

export class TypeTB implements ITypeTB {
  constructor(
    public id?: number,
    public code?: string,
    public ordre?: string,
    public type?: Type,
    public refBahrId?: number,
    public refRhythmId?: number
  ) {}
}
