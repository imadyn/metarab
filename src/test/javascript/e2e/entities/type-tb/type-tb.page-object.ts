import { by, element, ElementFinder } from 'protractor';

export class TypeTBComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-type-tb div table .btn-danger'));
  title = element.all(by.css('jhi-type-tb div h2#page-heading span')).first();

  async clickOnCreateButton(timeout?: number) {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(timeout?: number) {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons() {
    return this.deleteButtons.count();
  }

  async getTitle() {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class TypeTBUpdatePage {
  pageTitle = element(by.id('jhi-type-tb-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  codeInput = element(by.id('field_code'));
  ordreInput = element(by.id('field_ordre'));
  typeSelect = element(by.id('field_type'));
  refBahrSelect = element(by.id('field_refBahr'));
  refRhythmSelect = element(by.id('field_refRhythm'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCodeInput(code) {
    await this.codeInput.sendKeys(code);
  }

  async getCodeInput() {
    return await this.codeInput.getAttribute('value');
  }

  async setOrdreInput(ordre) {
    await this.ordreInput.sendKeys(ordre);
  }

  async getOrdreInput() {
    return await this.ordreInput.getAttribute('value');
  }

  async setTypeSelect(type) {
    await this.typeSelect.sendKeys(type);
  }

  async getTypeSelect() {
    return await this.typeSelect.element(by.css('option:checked')).getText();
  }

  async typeSelectLastOption(timeout?: number) {
    await this.typeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async refBahrSelectLastOption(timeout?: number) {
    await this.refBahrSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async refBahrSelectOption(option) {
    await this.refBahrSelect.sendKeys(option);
  }

  getRefBahrSelect(): ElementFinder {
    return this.refBahrSelect;
  }

  async getRefBahrSelectedOption() {
    return await this.refBahrSelect.element(by.css('option:checked')).getText();
  }

  async refRhythmSelectLastOption(timeout?: number) {
    await this.refRhythmSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async refRhythmSelectOption(option) {
    await this.refRhythmSelect.sendKeys(option);
  }

  getRefRhythmSelect(): ElementFinder {
    return this.refRhythmSelect;
  }

  async getRefRhythmSelectedOption() {
    return await this.refRhythmSelect.element(by.css('option:checked')).getText();
  }

  async save(timeout?: number) {
    await this.saveButton.click();
  }

  async cancel(timeout?: number) {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class TypeTBDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-typeTB-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-typeTB'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
