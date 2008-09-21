/*
 * Copyright 2002-2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.integration.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * {@link FileListFilter} that passes files only one time. This can
 * conveniently be used to prevent duplication of files, as is done in
 * {@link PollableFileSource}.
 * 
 * @author Iwein Fuld
 * 
 */
public class AcceptOnceFileFilter implements FileListFilter {

	private final Queue<File> seen;

	private final Object monitor = new Object();


	/**
	 * Creates an AcceptOnceFileFilter that is based on a bounded queue. If the
	 * queue overflows, files that fall out will be passed through this filter
	 * again if passed to the {@link #filterFiles(File[])} method.
	 * 
	 * @param maxCapacity the maximum number of Files to maintain in the 'seen'
	 * queue.
	 */
	public AcceptOnceFileFilter(int maxCapacity) {
		this.seen = new LinkedBlockingQueue<File>(maxCapacity);
	}

	/**
	 * Creates an AcceptOnceFileFilter based on an unbounded queue.
	 */
	public AcceptOnceFileFilter() {
		this.seen = new LinkedBlockingQueue<File>();
	}


	/**
	 * Returns the list of files that have not already been filtered by this
	 * instance.
	 */
	public List<File> filterFiles(File[] files) {
		List<File> accepted = new ArrayList<File>();
		for (File file : files) {
			if (accept(file)) {
				accepted.add(file);
			}
		}
		return accepted;
	}

	private boolean accept(File pathname) {
		synchronized (monitor) {
			if (seen.contains(pathname)) {
				return false;
			}
			if (!seen.offer(pathname)) {
				seen.poll();
				seen.add(pathname);
			}
			return true;
		}
	}

}
