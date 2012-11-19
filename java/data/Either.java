package data;


/**
 * @author mattf
 *
 * @param <L> the type of the "error"
 * @param <R> the type of the "success" value
 */
public abstract class Either<L, R> {
	
	private Either() {
		
	}
	
	public abstract boolean isRight();
	
	public abstract L getLeft();
	
	public abstract R getRight();
	
	public static class Left<L, R> extends Either<L, R> {

		private final L l;
		
		public Left(L l) {
			this.l = l;
		}
		
		@Override
		public boolean isRight() {
			return false;
		}

		@Override
		public L getLeft() {
			return this.l;
		}

		@Override
		public R getRight() {
			throw new RuntimeException("cannot get right projection:  object is a left");
		}
		
		public static <L, R> Left<L, R> of(L l) {
			return new Left<L, R>(l);
		}
		
	}
	
	public static class Right<L, R> extends Either<L, R> {

		private final R r;
		
		public Right(R r) {
			this.r = r;
		}
		
		@Override
		public boolean isRight() {
			return true;
		}

		@Override
		public L getLeft() {
			throw new RuntimeException("cannot get left projection:  object is a right");
		}

		@Override
		public R getRight() {
			return this.r;
		}
		
		public static <L, R> Right<L, R> of(R r) {
			return new Right<L, R>(r);
		}
		
	}
}