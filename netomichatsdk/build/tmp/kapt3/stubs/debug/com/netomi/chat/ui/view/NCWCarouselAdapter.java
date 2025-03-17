package com.netomi.chat.ui.view;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u0001\u0014B)\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0014\u0010\u0006\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\b\u0012\u0004\u0012\u00020\t0\u0007\u00a2\u0006\u0002\u0010\nJ\b\u0010\u000b\u001a\u00020\fH\u0016J\u001c\u0010\r\u001a\u00020\t2\n\u0010\u000e\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u000f\u001a\u00020\fH\u0016J\u001c\u0010\u0010\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\fH\u0016R\u001c\u0010\u0006\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\b\u0012\u0004\u0012\u00020\t0\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/netomi/chat/ui/view/NCWCarouselAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/netomi/chat/ui/view/NCWCarouselAdapter$CarouselViewHolder;", "items", "", "Lcom/netomi/chat/model/messages/NCWCarouselElement;", "carouselButton", "Lkotlin/Function1;", "Lcom/netomi/chat/model/messages/NCWCarouselButton;", "", "(Ljava/util/List;Lkotlin/jvm/functions/Function1;)V", "getItemCount", "", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "CarouselViewHolder", "netomichatsdk_debug"})
public final class NCWCarouselAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.netomi.chat.ui.view.NCWCarouselAdapter.CarouselViewHolder> {
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.netomi.chat.model.messages.NCWCarouselElement> items = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function1<com.netomi.chat.model.messages.NCWCarouselButton, kotlin.Unit> carouselButton = null;
    
    public NCWCarouselAdapter(@org.jetbrains.annotations.NotNull()
    java.util.List<com.netomi.chat.model.messages.NCWCarouselElement> items, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.netomi.chat.model.messages.NCWCarouselButton, kotlin.Unit> carouselButton) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public com.netomi.chat.ui.view.NCWCarouselAdapter.CarouselViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.netomi.chat.ui.view.NCWCarouselAdapter.CarouselViewHolder holder, int position) {
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0015\u001a\u00020\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0019\u001a\u00020\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0018\u00a8\u0006\u001b"}, d2 = {"Lcom/netomi/chat/ui/view/NCWCarouselAdapter$CarouselViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Lcom/netomi/chat/ui/view/NCWCarouselAdapter;Landroid/view/View;)V", "cardImageCarousel", "Landroidx/cardview/widget/CardView;", "getCardImageCarousel", "()Landroidx/cardview/widget/CardView;", "imgCarousel", "Landroid/widget/ImageView;", "getImgCarousel", "()Landroid/widget/ImageView;", "recyclerViewCarouselButton", "Landroidx/recyclerview/widget/RecyclerView;", "getRecyclerViewCarouselButton", "()Landroidx/recyclerview/widget/RecyclerView;", "rootLayout", "Landroidx/constraintlayout/widget/ConstraintLayout;", "getRootLayout", "()Landroidx/constraintlayout/widget/ConstraintLayout;", "tvDetail", "Landroid/widget/TextView;", "getTvDetail", "()Landroid/widget/TextView;", "tvHeading", "getTvHeading", "netomichatsdk_debug"})
    public final class CarouselViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull()
        private final androidx.constraintlayout.widget.ConstraintLayout rootLayout = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.ImageView imgCarousel = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView tvHeading = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView tvDetail = null;
        @org.jetbrains.annotations.NotNull()
        private final androidx.cardview.widget.CardView cardImageCarousel = null;
        @org.jetbrains.annotations.NotNull()
        private final androidx.recyclerview.widget.RecyclerView recyclerViewCarouselButton = null;
        
        public CarouselViewHolder(@org.jetbrains.annotations.NotNull()
        android.view.View itemView) {
            super(null);
        }
        
        @org.jetbrains.annotations.NotNull()
        public final androidx.constraintlayout.widget.ConstraintLayout getRootLayout() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.ImageView getImgCarousel() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.TextView getTvHeading() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.TextView getTvDetail() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final androidx.cardview.widget.CardView getCardImageCarousel() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final androidx.recyclerview.widget.RecyclerView getRecyclerViewCarouselButton() {
            return null;
        }
    }
}