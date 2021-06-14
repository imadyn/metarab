/**
 *
 */
const ARABE_ALPHABE_CODES: Array<number> = [
  1568,
  1569,
  1570,
  1571,
  1572,
  1573,
  1574,
  1575,
  1576,
  1577,
  1578,
  1579,
  1580,
  1581,
  1582,
  1583,
  1584,
  1585,
  1586,
  1587,
  1588,
  1589,
  1590,
  1591,
  1592,
  1593,
  1594,
  1595,
  1596,
  1597,
  1598,
  1599,
  1600,
  1601,
  1602,
  1603,
  1604,
  1605,
  1606,
  1607,
  1608,
  1609,
  1610
];

const ARABE_ALPHABE_LUNAIRE: Array<number> = [
  1568,
  1569,
  1570,
  1571,
  1572,
  1573,
  1574,
  1575,
  1576,
  1577,
  1578,
  1579,
  1580,
  1581,
  1582,
  1583,
  1584,
  1585,
  1586,
  1587,
  1588,
  1589,
  1590,
  1591,
  1592,
  1593,
  1594,
  1595,
  1596,
  1597,
  1598,
  1599,
  1600,
  1601,
  1602,
  1603,
  1604,
  1605,
  1606,
  1607,
  1608,
  1609,
  1610
];

const ARABE_ALPHABE_SOLAIRE: Array<number> = [
  1568,
  1569,
  1570,
  1571,
  1572,
  1573,
  1574,
  1575,
  1576,
  1577,
  1578,
  1579,
  1580,
  1581,
  1582,
  1583,
  1584,
  1585,
  1586,
  1587,
  1588,
  1589,
  1590,
  1591,
  1592,
  1593,
  1594,
  1595,
  1596,
  1597,
  1598,
  1599,
  1600,
  1601,
  1602,
  1603,
  1604,
  1605,
  1606,
  1607,
  1608,
  1609,
  1610
];

const ARABE_ALPHABET: Array<string> = [
  'ء',
  'ا',
  'ب',
  'ت',
  'ث',
  'ج',
  'ح',
  'خ',
  'د',
  'ذ',
  'ر',
  'ز',
  'س',
  'ش',
  'ص',
  'ض',
  'ط',
  'ظ	',
  'ع',
  'غ',
  'ف',
  'ق',
  'ك',
  'ل',
  'م',
  'ن',
  'ه',
  'و',
  'ي',
  'ﺓ',
  'ﻯ'
];

const ARABE_MOUVMT_CODES: Array<number> = [32, 1611, 1612, 1613, 1614, 1615, 1616, 1617, 1618, 1648];

const ARABE_MOUVMT_UN: Array<number> = [1614, 1615, 1616];

const ARABE_MOUVMT_ZERO: Array<number> = [1617, 1618, 1648];

export class Caractere {
  letter?: string;
  binary?: string;
  indice?: number;
  isLettre?: boolean;
  isSpace?: boolean;

  constructor(public caractere?: string, public position?: number) {
    this.letter = caractere;
    this.binary = '';

    this.indice = ARABE_ALPHABE_CODES.indexOf(this.letter.charCodeAt(0));
    this.isLettre = true;
    this.isSpace = false;
    if (this.indice === -1) {
      this.isLettre = false;
      this.indice = ARABE_MOUVMT_CODES.indexOf(this.letter.charCodeAt(0));
      if (this.letter.charCodeAt(0) === 32) {
        this.isSpace = true;
      }

      const val: number = ARABE_MOUVMT_CODES[this.indice];

      if (ARABE_MOUVMT_UN.filter(e => e === val).length > 0) {
        this.binary = '1';
      } else if (ARABE_MOUVMT_ZERO.filter(e => e === val).length > 0) {
        this.binary = '0';
      } else {
        this.binary = '01';
      }
    } else {
      this.position = position;
    }
  }
}

export class CharcterArab {
  hasPrecedent?: boolean;
  binary?: string;
  nbreChar?: number;
  pas?: number;
  tripplet?: Caractere[];
  isAlef?: boolean;
  isLam?: boolean;

  constructor(arrayChar: string[], hasPrecedent: boolean, public position?: number) {
    this.hasPrecedent = hasPrecedent;
    this.tripplet = new Array();
    this.binary = '';
    let g = -1;
    const max = 5;
    let retour = false;
    for (let i = 0; i < arrayChar.length; i++) {
      const chArabe = new Caractere(arrayChar[i], position);

      this.nbreChar = position;
      position++;
      if (chArabe.isLettre) {
        this.tripplet.push(chArabe);

        this.isAlef = chArabe.letter.charCodeAt(0) === 1575;
        this.isLam = chArabe.letter.charCodeAt(0) === 1604;

        g++;
        const maxElement = i + max;
        for (let j = i + 1; j < maxElement && j < arrayChar.length; j++) {
          const chArabeMouvmt = new Caractere(arrayChar[j], position);
          this.nbreChar = position;
          if (!chArabeMouvmt.isLettre) {
            this.tripplet.push(chArabeMouvmt);
            this.binary = chArabeMouvmt.binary + this.binary;
            position++;
            g++;
          } else {
            retour = true;
            break;
          }
        }
        if (retour) {
          break;
        }
      } else {
        break;
      }
      if (this.tripplet.length === max) {
        break;
      }
    }
    this.pas = g;

    if (this.binary === '') {
      this.binary = '0';
    }
  }

  public transforms() {
    let t = '';
    if (this.tripplet.length < 2) {
      t = '0';
    } else {
      for (let i = 0; i < this.tripplet.length; i++) {
        const d = this.tripplet[i];
        if (d) {
          t = d.binary + t;
        }
      }
    }
    return t;
  }
}

export class MotArabe {
  binary?: string;
  caracters?: CharcterArab[];

  constructor(text: string, public position?: number) {
    const arrayMot = text.split('');
    /**const arrayMot = array[0];*/
    this.position = position;
    this.caracters = new Array();
    this.binary = '';
    let hasPrecedent = false;
    let positionChar = 1;

    for (let i = 0; i < arrayMot.length; i++) {
      const chArab = new CharcterArab(arrayMot.slice(i, arrayMot.length), hasPrecedent, positionChar);
      for (let j = 0; j < chArab.tripplet.length; j++) {
        const caractere = chArab.tripplet[j];
        hasPrecedent = true;
        if (caractere && caractere.isSpace) {
          hasPrecedent = false;
          break;
        }
      }

      if (chArab.tripplet.length > 0) {
        this.caracters.push(chArab);
        positionChar = chArab.nbreChar;
        this.binary = chArab.binary + this.binary;
      }
    }

    if (this.caracters.length > 2 && this.caracters[0].isAlef && this.caracters[1].isLam) {
      const val = ARABE_ALPHABE_CODES[this.caracters[3].tripplet[0].indice];

      if (this.position === 1) {
        if (ARABE_ALPHABE_SOLAIRE.filter(e => e === val).length > 0) {
          this.binary = this.binary.substring(0, this.binary.length - 2) + '1';

          /*	"1000"   "101"*/
        } else {
          /*	"100"     "101"*/
          this.binary = this.binary.substring(0, this.binary.length - 1) + '1';
        }
      } else {
        if (ARABE_ALPHABE_SOLAIRE.filter(e => e === val).length > 0) {
          /*	"1000"		"10"
          //console.log("BINARY :	"+ this.binary + "   BINARY TRANSFORM   :" + this.binary.substring(0 , this.binary.length -2 ) );*/

          this.binary = this.binary.substring(0, this.binary.length - 2);
        } else {
          /*	"100"       "10"*/
          this.binary = this.binary.substring(0, this.binary.length - 1);
        }
      }
    }
  }

  public transforms() {
    let t = '';
    for (let i = 0; i < this.caracters.length; i++) {
      t = this.caracters[i].transforms() + t;
    }
    return t;
  }
}

export class Phrase {
  binary?: string;
  mots?: MotArabe[];

  constructor(text: string, public position?: number) {
    const arrayMot = text.split(' ');
    this.position = position;
    this.mots = new Array();
    this.binary = '';
    for (let i = 0; i < arrayMot.length; i++) {
      const motArabe = new MotArabe(arrayMot[i], position);
      this.mots.push(motArabe);
      this.binary = motArabe.binary + this.binary;
      position++;
    }
  }

  public transforms() {
    let t = '';
    for (let i = 0; i < this.mots.length; i++) {
      t = this.mots[i].transforms() + t;
    }
    return t;
  }
}

export class Paragraphe {
  binary?: string;
  phrases?: Phrase[];

  valeurRhythmPart1?: string;
  valeurRhythmPart2?: string;

  constructor(part1: string, part2?: string) {
    this.phrases = new Array();
    this.binary = '';
    let position = 1;

    this.phrases.push(new Phrase(part1, position));
    position++;
    if (part2) {
      this.phrases.push(new Phrase(part2, position));
    }
  }

  public transforms() {
    const size = this.phrases.length;
    if (size > 0) {
      this.valeurRhythmPart1 = this.phrases[0].binary;
    }
    if (size === 2) {
      this.valeurRhythmPart2 = this.phrases[1].binary;
    }
    this.binary = this.valeurRhythmPart2 + this.valeurRhythmPart1;
  }
}
