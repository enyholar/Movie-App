package com.behruz.magmovie.viewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.annotation.NonNull;


/**
 * Created by mishael.harry on 4/3/2018.
 */

public class QuestionViewModel extends AndroidViewModel {
    public QuestionViewModel(@NonNull Application application) {
        super(application);
    }

//    private RemoteQuestionRepository remoteQuestionRepository;
//    private LocalQuestionRepository localQuestionRepository;
//    private CompositeDisposable disposable;
//    private MutableLiveData<List<Question>> questionLiveData;
//
//    public QuestionViewModel(Application application) {
//        super(application);
//        disposable = new CompositeDisposable();
//        questionLiveData = new MutableLiveData<>();
//        localQuestionRepository = new LocalQuestionRepository(application);
//        remoteQuestionRepository = new RemoteQuestionRepository(application);
//        fetchLocalQuestions();
//    }
//
//    public LiveData<List<Question>> getQuestions(){
//        return questionLiveData;
//    }
//
//    private void fetchLocalQuestions(){
//        disposable.add(localQuestionRepository.getQuestion()
//                  .subscribeOn(Schedulers.io())
//                  .observeOn(AndroidSchedulers.mainThread())
//                  .subscribeWith(new DisposableSingleObserver<List<Question>>() {
//                      @Override
//                      public void onSuccess(List<Question> questionList) {
//                          localQuestionRepository.deleteQuestions();
//                          if (questionList != null && questionList.size() > 0){
//                              questionLiveData.setValue(questionList);
//                              fetchRemoteQuestions();
//                          } else {
//                              fetchRemoteQuestions();
//                          }
//                      }
//
//                      @Override
//                      public void onError(Throwable e) {
//                          questionLiveData.setValue(null);
//                      }
//                  }));
//    }
//
//    private void fetchRemoteQuestions(){
//        disposable.add(remoteQuestionRepository.getQuestions()
//                  .subscribeOn(Schedulers.io())
//                  .observeOn(AndroidSchedulers.mainThread())
//                  .subscribeWith(new DisposableSingleObserver<BaseResponse<List<Question>>>() {
//                      @Override
//                      public void onSuccess(BaseResponse<List<Question>> response) {
//                          if (response != null && response.isSucceeded()){
//                              if (response.getResult().size() > 0){
//                                  localQuestionRepository.insertQuestions(response.getResult());
//                                  questionLiveData.setValue(response.getResult());
//                              }
//                          }
//                      }
//
//                      @Override
//                      public void onError(Throwable e) {
//                      }
//                  }));
//    }
//
//    @Override
//    protected void onCleared() {
//        if (disposable != null)disposable.dispose();
//    }
}
