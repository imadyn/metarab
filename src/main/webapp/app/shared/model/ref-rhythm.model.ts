export const enum Transform {
  IDENTITE = 'IDENTITE',
  KHABN = 'KHABN',
  WAKS = 'WAKS',
  IDMAR = 'IDMAR',
  TAY = 'TAY',
  KABD = 'KABD',
  AKLE = 'AKLE',
  ASBE = 'ASBE',
  KAF = 'KAF',
  KHABL = 'KHABL',
  KHAZL = 'KHAZL',
  CHAKL = 'CHAKL',
  NAKS = 'NAKS',
  TATHBIL = 'TATHBIL',
  TARFIL = 'TARFIL',
  TASBIGH = 'TASBIGH',
  HATHF = 'HATHF',
  KATF = 'KATF',
  KATAE = 'KATAE',
  BATR = 'BATR',
  KASR = 'KASR',
  HATHATH = 'HATHATH',
  SALAM = 'SALAM',
  WAKF = 'WAKF',
  KACHF = 'KACHF',
  KHARM = 'KHARM',
  TACHEIT = 'TACHEIT',
  KABL = 'KABL'
}

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
