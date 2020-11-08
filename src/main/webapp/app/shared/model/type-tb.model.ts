import { Transform } from '../enum/transform-enum';

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
  refRhythmName?: string;
  refRhythmValeur?: string;
  refRhythmTransform?: Transform;
}

export class TypeTB implements ITypeTB {
  constructor(
    public id?: number,
    public code?: string,
    public ordre?: string,
    public type?: Type,
    public refBahrId?: number,
    public refRhythmId?: number,
    public refRhythmName?: string,
    public refRhythmValeur?: string,
    public refRhythmTransform?: Transform
  ) {}
}
