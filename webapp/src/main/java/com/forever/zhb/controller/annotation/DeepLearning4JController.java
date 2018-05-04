package com.forever.zhb.controller.annotation;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.deeplearning4j.datasets.iterator.impl.MnistDataSetIterator;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.Updater;
import org.deeplearning4j.nn.conf.layers.ConvolutionLayer;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.conf.layers.SubsamplingLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.nd4j.linalg.lossfunctions.LossFunctions.LossFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/htgl/deepLearning4JController")
public class DeepLearning4JController {
	
	private static  Logger log = LoggerFactory.getLogger(DeepLearning4JController.class); 
	
	@RequestMapping(value="/toDeepLearning4J",method=RequestMethod.GET)
	public String toDeepLearning4J(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		final int numRows = 28; // 矩阵的行数。
	    final int numColumns = 28; // 矩阵的列数。
	    int outputNum = 10; // 潜在结果（比如0到9的整数标签）的数量。
	    int batchSize = 128; // 每一步抓取的样例数量。
	    int rngSeed = 123; // 这个随机数生成器用一个随机种子来确保训练时使用的初始权重维持一致。下文将会说明这一点的重要性。
	    int numEpochs = 15; // 一个epoch指将给定数据集全部处理一遍的周期。
	    int nEpochs = 10;       //total rounds of training  
	    
	    log.info("Load data....");  
		log.info(System.getProperty("user.home"));
	    DataSetIterator mnistTrain = new MnistDataSetIterator(batchSize, true, rngSeed);
	    DataSetIterator mnistTest = new MnistDataSetIterator(batchSize, false, rngSeed);
	    
	    MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
	            .seed(rngSeed)
	            .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
	            //.setMaxNumLineSearchIterations(1)
	            //.learningRate(0.006)
	            .updater(Updater.NESTEROVS)
	            //.momentum(0.9)
	            //.regularization(true).l2(1e-4)
	            .list()
	            .layer(0, new DenseLayer.Builder()
	                    .nIn(numRows * numColumns) // Number of input datapoints.
	                    .nOut(1000) // Number of output datapoints.
	                    //.activation("relu") // Activation function.
	                    .weightInit(WeightInit.XAVIER) // Weight initialization.
	                    .build())
	            .layer(1, new OutputLayer.Builder(LossFunction.NEGATIVELOGLIKELIHOOD)
	                    .nIn(1000)
	                    .nOut(outputNum)
	                    //.activation("softmax")
	                    .weightInit(WeightInit.XAVIER)
	                    .build())
	            .pretrain(false).backprop(true)
	            .build();
	   
	    MultiLayerNetwork model = new MultiLayerNetwork(conf);  
		model.init();      
		 // a listener which can print loss function score after each iteration
		model.setListeners(new ScoreIterationListener(1));          
		
		for( int i = 0; i < nEpochs; ++i ) {  
		    model.fit(mnistTrain);  
		    log.info("*** Completed epoch " + i + "***");  
		  
		    log.info("Evaluate model....");  
		    Evaluation eval = new Evaluation(outputNum);  
		    while(mnistTest.hasNext()){  
		        DataSet ds = mnistTest.next();            
		        INDArray output = model.output(ds.getFeatureMatrix(), false);  
		        eval.eval(ds.getLabels(), output);  
		    }  
		    log.info(eval.stats());  
		    mnistTest.reset();  
		}  
		
		return "htgl.spider.index";
	}
	
	/*@RequestMapping(value="/toDeepLearning4JTest",method=RequestMethod.GET)
	public void toDeepLearning4JTest(HttpServletRequest request,HttpServletResponse response) throws IOException{
		int nChannels = 1;      //black & white picture, 3 if color image  
		int outputNum = 10;     //number of classification  
		int batchSize = 64;     //mini batch size for sgd  
		int nEpochs = 10;       //total rounds of training  
		int iterations = 1;     //number of iteration in each traning round  
		int seed = 123;         //random seed for initialize weights  
		  
		log.info("Load data....");  
		log.info(System.getProperty("user.home"));
		DataSetIterator mnistTrain = new MnistDataSetIterator(batchSize, true, 12345);  
		DataSetIterator mnistTest = new MnistDataSetIterator(batchSize, false, 12345);  
		
		MultiLayerConfiguration.Builder builder = new NeuralNetConfiguration.Builder()  
		        .seed(seed)  
		        .iterations(iterations)  
		        .regularization(true).l2(0.0005)  
		        .learningRate(0.01)//.biasLearningRate(0.02)  
		        //.learningRateDecayPolicy(LearningRatePolicy.Inverse).lrPolicyDecayRate(0.001).lrPolicyPower(0.75)  
		        .weightInit(WeightInit.XAVIER)  
		        .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)  
		        .updater(Updater.NESTEROVS).momentum(0.9)  
		        .list()  
		        .layer(0, new ConvolutionLayer.Builder(5, 5)  
		                //nIn and nOut specify depth. nIn here is the nChannels and nOut is the number of filters to be applied  
		                .nIn(nChannels)  
		                .stride(1, 1)  
		                .nOut(20)  
		                .activation("identity")  
		                .build())  
		        .layer(1, new SubsamplingLayer.Builder(SubsamplingLayer.PoolingType.MAX)  
		                .kernelSize(2,2)  
		                .stride(2,2)  
		                .build())  
		        .layer(2, new ConvolutionLayer.Builder(5, 5)  
		                //Note that nIn need not be specified in later layers  
		                .stride(1, 1)  
		                .nOut(50)  
		                .activation("identity")  
		                .build())  
		        .layer(3, new SubsamplingLayer.Builder(SubsamplingLayer.PoolingType.MAX)  
		                .kernelSize(2,2)  
		                .stride(2,2)  
		                .build())  
		        .layer(4, new DenseLayer.Builder().activation("relu")  
		                .nOut(500).build())  
		        .layer(5, new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)  
		                .nOut(outputNum)  
		                .activation("softmax")  
		                .build())  
		        .backprop(true).pretrain(false)  
		        .cnnInputSize(28, 28, 1);  
		
		// The builder needs the dimensions of the image along with the number of channels. these are 28x28 images in one channel  
		//new ConvolutionLayerSetup(builder,28,28,1);  
		MultiLayerConfiguration conf = builder.build();  
		MultiLayerNetwork model = new MultiLayerNetwork(conf);  
		model.init();      
		 // a listener which can print loss function score after each iteration
		model.setListeners(new ScoreIterationListener(1));          
		
		for( int i = 0; i < nEpochs; ++i ) {  
		    model.fit(mnistTrain);  
		    log.info("*** Completed epoch " + i + "***");  
		  
		    log.info("Evaluate model....");  
		    Evaluation eval = new Evaluation(outputNum);  
		    while(mnistTest.hasNext()){  
		        DataSet ds = mnistTest.next();            
		        INDArray output = model.output(ds.getFeatureMatrix(), false);  
		        eval.eval(ds.getLabels(), output);  
		    }  
		    log.info(eval.stats());  
		    mnistTest.reset();  
		}  
	}*/
	
	@RequestMapping(value="nd4j",method=RequestMethod.GET)
	public void nd4j(HttpServletRequest request,HttpServletResponse response){
		
	}
	
	public static void main(String[] args)  {
		/*int[][] arr = new int[][] { { 1, 2, 3 }, { 5, 3, 6, 6 }, { 0 } };
		for (int i = 0; i < arr.length; i++) { // 遍历二维数组，遍历出来的每一个元素是一个一维数组
			for (int j = 0; j < arr[i].length; j++) { // 遍历对应位置上的一维数组
				System.out.print(arr[i][j] + "  ");
			}
			System.out.println();
		}*/
		
		INDArray nd = Nd4j.create(new float[]{1, 2, 3, 4}, new int[]{2, 2});
		System.out.println(nd);
	}

}
