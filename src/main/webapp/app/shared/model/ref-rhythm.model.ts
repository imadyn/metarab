import { Transform } from '../enum/transform-enum';

export interface IRefRhythm {
  id?: number;
  code?: string;
  name?: string;
  valeur?: string;
  transform?: Transform;
  parentId?: number;
}

export class RefRhythm implements IRefRhythm {
  constructor(
    public id?: number,
    public code?: string,
    public name?: string,
    public valeur?: string,
    public transform?: Transform,
    public parentId?: number
  ) {}
}
