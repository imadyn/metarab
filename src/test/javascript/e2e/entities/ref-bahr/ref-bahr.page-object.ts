import { by, element, ElementFinder } from 'protractor';

export class RefBahrComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-ref-bahr div table .btn-danger'));
  title = element.all(by.css('jhi-ref-bahr div h2#page-heading span')).first();

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

export class RefBahrUpdatePage {
  pageTitle = element(by.id('jhi-ref-bahr-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  codeInput = element(by.id('field_code'));
  nameInput = element(by.id('field_name'));
  signatureInput = element(by.id('field_signature'));
  styleSelect = element(by.id('field_style'));
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

  async setSignatureInput(signature) {
    await this.signatureInput.sendKeys(signature);
  }

  async getSignatureInput() {
    return await this.signatureInput.getAttribute('value');
  }

  async setStyleSelect(style) {
    await this.styleSelect.sendKeys(style);
  }

  async getStyleSelect() {
    return await this.styleSelect.element(by.css('option:checked')).getText();
  }

  async styleSelectLastOption(timeout?: number) {
    await this.styleSelect
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

export class RefBahrDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-refBahr-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-refBahr'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
