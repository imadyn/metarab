import { by, element, ElementFinder } from 'protractor';

export class RefRhythmComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-ref-rhythm div table .btn-danger'));
  title = element.all(by.css('jhi-ref-rhythm div h2#page-heading span')).first();

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

export class RefRhythmUpdatePage {
  pageTitle = element(by.id('jhi-ref-rhythm-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  codeInput = element(by.id('field_code'));
  nameInput = element(by.id('field_name'));
  valeurInput = element(by.id('field_valeur'));
  transformSelect = element(by.id('field_transform'));
  parentSelect = element(by.id('field_parent'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCodeInput(code) {
    await this.codeInput.sendKeys(code);
  }

  async getCodeInput() {
    return await this.codeInput.getAttribute('value');
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return await this.nameInput.getAttribute('value');
  }

  async setValeurInput(valeur) {
    await this.valeurInput.sendKeys(valeur);
  }

  async getValeurInput() {
    return await this.valeurInput.getAttribute('value');
  }

  async setTransformSelect(transform) {
    await this.transformSelect.sendKeys(transform);
  }

  async getTransformSelect() {
    return await this.transformSelect.element(by.css('option:checked')).getText();
  }

  async transformSelectLastOption(timeout?: number) {
    await this.transformSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async parentSelectLastOption(timeout?: number) {
    await this.parentSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async parentSelectOption(option) {
    await this.parentSelect.sendKeys(option);
  }

  getParentSelect(): ElementFinder {
    return this.parentSelect;
  }

  async getParentSelectedOption() {
    return await this.parentSelect.element(by.css('option:checked')).getText();
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

export class RefRhythmDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-refRhythm-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-refRhythm'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
