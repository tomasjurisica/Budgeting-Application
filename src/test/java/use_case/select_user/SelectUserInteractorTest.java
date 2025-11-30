package use_case.select_user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SelectUserInteractorTest {

    private SelectUserInteractor interactor;

    @BeforeEach
    void setUp() {
        // Presenter provided per-test inside each test method.
        // Interactor initialized there.
    }

    @Test
    void testSuccessfulSelectUser() {
        SelectUserInputData inputData = new SelectUserInputData("Alice");

        SelectUserOutputBoundary successPresenter = new SelectUserOutputBoundary() {
            @Override
            public void execute(SelectUserInputData inputData) {

            }

            @Override
            public void present(SelectUserOutputData outputData) {
                assertNotNull(outputData);
                assertEquals("Alice", outputData.roommateName());
            }
        };

        interactor = new SelectUserInteractor(successPresenter);
        interactor.execute(inputData);
    }

    @Test
    void testInteractorDoesNotModifyName() {
        SelectUserInputData inputData = new SelectUserInputData("Bob");

        SelectUserOutputBoundary presenter = new SelectUserOutputBoundary() {
            @Override
            public void execute(SelectUserInputData inputData) {

            }

            @Override
            public void present(SelectUserOutputData outputData) {
                // Ensures the interactor forwards the name exactly as given
                assertEquals("Bob", outputData.roommateName());
                assertSame("Bob", outputData.roommateName());
            }
        };

        interactor = new SelectUserInteractor(presenter);
        interactor.execute(inputData);
    }

    @Test
    void testPresenterIsCalledOnce() {
        SelectUserInputData inputData = new SelectUserInputData("Charlie");

        final int[] callCount = {0};

        SelectUserOutputBoundary presenter = new SelectUserOutputBoundary() {
            @Override
            public void execute(SelectUserInputData inputData) {

            }

            @Override
            public void present(SelectUserOutputData outputData) {
                callCount[0]++;
                assertEquals("Charlie", outputData.roommateName());
            }
        };

        interactor = new SelectUserInteractor(presenter);
        interactor.execute(inputData);

        assertEquals(1, callCount[0], "Presenter should be called exactly once");
    }
}
