//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package presenters;

import usecases.addentry.AddEntryResponseModel;
import viewmodels.HomePageViewModel;

public class AddEntryPresenterImpl implements AddEntryPresenter {
    private final HomePageViewModel viewModel;

    public AddEntryPresenterImpl(HomePageViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void present(AddEntryResponseModel model) {
        if (model.success && model.entry != null) {
            this.viewModel.addEntry(model.entry);
            this.viewModel.setLastMessage("Entry added");
        } else {
            this.viewModel.setLastMessage(model.message);
        }

    }
}
