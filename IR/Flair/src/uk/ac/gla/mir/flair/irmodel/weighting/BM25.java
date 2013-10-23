package uk.ac.gla.mir.flair.irmodel.weighting;

/**
 * Title:        BM25<br/>
 * Description:  Weighting Model based on the BM25
 * To use specify in the flair.query.spec 
 * weighting = BM25<br/>
 * Copyright:    Copyright (c) 2010<br/>
 * Company:      Department of Computing Science, University of Glasgow<br/>
 * @author David Hannah
 * @version 1.0
 */
public class BM25 extends Weighting {
	
	/**
	 * k1 = param1
	 * b = param2
	 * k3 = parmam3
	 */
	
	/* (non-Javadoc)
	 * @see uk.ac.gla.mir.flair.irmodel.weighting.Weighting#score()
	 */
	@Override
	public double score() {
		double param1 =  1.2;
		double param2 = 0.75;
		double param3 = 8.0;
		if( params != null && params.length >= 3 ){	
			param1 =  params[0];
			param2 = params[1];
			param3 = params[2];
		}
		double num =  (double)termFrequencyForDocument * (param3 + 1d) * (double)termFrequencyForQuery;
		double den = (param3 + 
					  termFrequencyForQuery) * 
					  	( param1 *
								( (1-param2) + 
										param2 * 
											( (double)documentLength / averageDocumentLength ) 
								)
						+ (double)termFrequencyForDocument);
		return num / den;
	}
	
}
