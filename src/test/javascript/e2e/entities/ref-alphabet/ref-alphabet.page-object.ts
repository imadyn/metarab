import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class RefAlphabetComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-ref-alphabet div table .btn-danger'));
  title = element.all(by.css('jhi-ref-alphabet div h2#page-heading span')).first();

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

export class RefAlphabetUpdatePage {
  pageTitle = element(by.id('jhi-ref-alphabet-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  codeInput = element(by.id('field_code'));
  nameInput = element(by.id('field_name'));
  rhythmInput = element(by.id('field_rhythm'));
  languageSelect = element(by.id('field_language'));

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

  async setRhythmInput(rhythm) {
    await this.rhythmInput.sendKeys(rhythm);
  }

  async getRhythmInput() {
    return await this.rhythmInput.getAttribute('value');
  }

  async setLanguageSelect(language) {
    await this.languageSelect.sendKeys(language);
  }

  async getLanguageSelect() {
    return await this.languageSelect.element(by.css('option:checked')).getText();
  }

  async languageSelectLastOption(timeout?: number) {
    await this.languageSelect
      .all(by.tagName('option'))
      .last()
      .click();
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

export class RefAlphabetDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-refAlphabet-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-refAlphabet'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
